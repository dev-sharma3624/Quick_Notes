package com.example.quicknotes.repositories

import com.example.quicknotes.data.data_classes.Note
import com.example.quicknotes.data.db.NoteDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val dao : NoteDao
) {
    fun signUpWithEmail(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) onResult(true, null)
                else onResult(false, task.exception?.message)
            }
    }

    fun signInWithEmail(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) onResult(true, null)
                else onResult(false, task.exception?.message)
            }
    }

    fun signInWithGoogle(idToken: String, onResult: (Boolean, String?) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) onResult(true, null)
                else onResult(false, task.exception?.message)
            }
    }

    fun currentUser() = auth.currentUser

    fun signOut() = auth.signOut()

    suspend fun saveNote(note: Note) : Note {
        val uid = currentUser()?.uid ?: return Note()

        var noteId = note.id

        if (noteId.isEmpty()) {
            noteId = firestore.collection("users")
                .document(uid)
                .collection("notes")
                .document()
                .id

            val newNote = Note(
                noteId,
                note.title,
                note.content,
                note.lastUpdated
            )

            saveNoteToFireStoreAndDb(uid, noteId, newNote)

            return newNote
        }else{
            saveNoteToFireStoreAndDb(
                uid,
                noteId,
                note
            )

            return note
        }
    }

    private suspend fun saveNoteToFireStoreAndDb(
        uid : String,
        noteId : String,
        note: Note,

    ){
        firestore.collection("users")
            .document(uid)
            .collection("notes")
            .document(noteId)
            .set(note)
            .await()

        dao.insert(note)
    }

    fun getNotes() : Flow<List<Note>> = callbackFlow {

        val authListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                close()
            }
        }

        auth.addAuthStateListener(authListener)

        val uid = auth.currentUser?.uid ?: run {
            close()
            return@callbackFlow
        }

        val subscription = uid.let {
            firestore.collection("users")
                .document(it)
                .collection("notes")
                .orderBy("lastUpdated", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    snapshot?.let { snap ->
                        trySend(snap.documents.mapNotNull { doc ->
                            doc.toObject(Note::class.java)
                        }).isSuccess
                    }
                }
        }

        awaitClose {
            subscription.remove()
            auth.removeAuthStateListener(authListener)
        }
    }

}
