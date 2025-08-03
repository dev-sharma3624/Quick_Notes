package com.example.quicknotes

import android.app.Application
import com.example.quicknotes.repositories.FirebaseAuthRepository
import com.example.quicknotes.viewModel.AuthenticationVm
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

class QuickNotes : Application() {

    override fun onCreate() {
        super.onCreate()


        startKoin{

            val firebaseModule = module {
                single { FirebaseAuth.getInstance()}
                single { FirebaseAuthRepository(get()) }
            }

            val viewModel = module{
                viewModel{AuthenticationVm(get())}
            }

            androidContext(this@QuickNotes)
            modules(listOf(
                firebaseModule,
                viewModel
            ))
        }
    }

}