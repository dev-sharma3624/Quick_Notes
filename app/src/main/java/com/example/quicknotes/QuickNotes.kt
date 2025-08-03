package com.example.quicknotes

import android.app.Application
import androidx.room.Room
import com.example.quicknotes.data.db.AppDb
import com.example.quicknotes.repositories.FirebaseRepository
import com.example.quicknotes.viewModel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

class QuickNotes : Application() {

    override fun onCreate() {
        super.onCreate()


        startKoin{


            androidContext(this@QuickNotes)

            val firebaseModule = module {
                single { FirebaseAuth.getInstance()}
                single { FirebaseFirestore.getInstance()}
                single { FirebaseRepository(get(), get(), get()) }
            }

            val viewModel = module{
                viewModel{MainViewModel(get(), get())}
            }

            val db = module {
                single {
                    Room.databaseBuilder(
                        get(),
                        AppDb::class.java,
                        "App_Db"
                    ).build()
                }

                single { get<AppDb>().noteDao() }
            }

            modules(listOf(
                firebaseModule,
                viewModel,
                db
            ))
        }
    }

}