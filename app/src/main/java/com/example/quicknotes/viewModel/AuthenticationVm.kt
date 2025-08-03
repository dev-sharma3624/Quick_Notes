package com.example.quicknotes.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicknotes.R
import com.example.quicknotes.repositories.FirebaseAuthRepository
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch

class AuthenticationVm(
    private val repo: FirebaseAuthRepository
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

}