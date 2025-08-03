package com.example.quicknotes.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quicknotes.R
import com.example.quicknotes.data.data_classes.Note
import com.example.quicknotes.data.db.NoteDao
import com.example.quicknotes.repositories.FirebaseRepository
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(
    private val repo: FirebaseRepository,
    private val dao : NoteDao
) : ViewModel() {

    private val _currentTab = mutableStateOf("Sign Up")
    val currentTab : State<String> = _currentTab

    private val _emailField = mutableStateOf("")
    val emailField : State<String> = _emailField

    private val _pwdField = mutableStateOf("")
    val pwdField : State<String> = _pwdField

    private val _showPwd = mutableStateOf(false)
    val showPwd : State<Boolean> = _showPwd

    private val _isEmailProgressActive = mutableStateOf(false)
     val isEmailProgressActive : State<Boolean> = _isEmailProgressActive

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage : State<String?> = _errorMessage

    private val _notes = MutableStateFlow<List<Note>?>(null)
    val notes: StateFlow<List<Note>?> = _notes

    private var _noteId : String? = null

    val individualNote = mutableStateOf<Note?>(null)

    init {
        if(isSignedIn()){
            observeNotes()
        }
    }

    private fun observeNotes() {
        viewModelScope.launch {
            repo.getNotes()
                .collect { notesList ->
                    _notes.value = notesList
                }
        }
    }

    fun fetchNotes(){
        viewModelScope.launch {
            try {
                repo.getNotes()
                    .collect { notesList ->
                        _notes.value = notesList
                        if(!isSignedIn()){
                            notesList.forEach {
                                dao.insert(it)
                            }
                        }
                    }
            }catch (e : Exception){
                Log.e("Exception", "${e.message}")
            }
        }
    }

    fun getNoteById() {
        viewModelScope.launch {
            individualNote.value =  if(_noteId != null){
                notes
                    .map { list -> list?.find { it.id == _noteId } ?: Note() }
                    .first()
            }else{
                Note()
            }
        }
    }

    fun setNoteId(s : String){
        _noteId = s
        getNoteById()
    }

    fun saveNote(){
        viewModelScope.launch(Dispatchers.IO + NonCancellable) {
            individualNote.value?.let {
                individualNote.value = repo.saveNote(it)
            }
        }
    }

    fun setEmailField(s : String){
        _emailField.value = s
    }

    fun setPwdField(s : String){
        _pwdField.value = s
    }

    fun setCurrentTab(){
        _errorMessage.value = null
        if(_currentTab.value == "Sign In"){
            _currentTab.value = "Sign Up"
        }else{
            _currentTab.value = "Sign In"
        }
    }

    fun setShowPwd(){
        _showPwd.value = !_showPwd.value
    }

    fun setIsEmailProgressActive(){
        _isEmailProgressActive.value = !isEmailProgressActive.value
    }

    private fun signInEmail(email: String, pass: String, onResult: (Boolean, String?) -> Unit) {
        repo.signInWithEmail(email, pass, onResult)
    }

    private fun signUpEmail(email: String, pass: String, onResult: (Boolean, String?) -> Unit) {
        repo.signUpWithEmail(email, pass, onResult)
    }

    fun signInGoogle(context : Context, onResult: (Boolean, String?) -> Unit) {
        val request = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.default_web_client_id))
            .build()

        val credentialManager = CredentialManager.create(context)

        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = GetCredentialRequest(listOf(request)),
                    context = context
                )

                val credential = result.credential
                if (credential is GoogleIdTokenCredential) {
                    val idToken = credential.idToken
                    repo.signInWithGoogle(idToken, onResult)
                }
            } catch (e: Exception) {
                Log.e("GoogleSignIn", "Sign in failed", e)
            }
        }

    }

    fun isSignedIn() = repo.currentUser() != null


    fun onClickButton(onResult: (Boolean, String?) -> Unit) {

        if(_emailField.value.isBlank()){

        }else if(_pwdField.value.isBlank()){

        }else{

            setIsEmailProgressActive()

            when(_currentTab.value){

                "Sign In" -> signInEmail(
                    _emailField.value,
                    _pwdField.value,
                    onResult
                )

                "Sign Up" -> signUpEmail(
                    _emailField.value,
                    _pwdField.value,
                    {b, s ->
                        _errorMessage.value = s
                        onResult(b, s)
                    }
                )

            }
        }
    }

    fun signOut(){
        repo.signOut()
        viewModelScope.launch(Dispatchers.IO + NonCancellable) {
            dao.deleteAll()
        }
    }

    fun getUserName() : String?{
        return repo.currentUser()!!.displayName
    }

}