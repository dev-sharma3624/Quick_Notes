package com.example.quicknotes

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quicknotes.data.enum_classes.Screen
import com.example.quicknotes.ui.screen.LoginScreen
import com.example.quicknotes.ui.screen.MainScreen
import com.example.quicknotes.ui.screen.NotesEditScreen
import com.example.quicknotes.ui.theme.QuickNotesTheme
import com.example.quicknotes.viewModel.MainViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickNotesTheme {
                Column(
                    Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                        .paint(
                            painter = painterResource(R.drawable.bg),
                            contentScale = ContentScale.FillBounds
                        )
                ) {
                    NavigationGraph()
                }
            }
        }
    }
}

@Composable
fun NavigationGraph() {

    val navController = rememberNavController()
    val mainViewModel : MainViewModel = koinViewModel()

    val startDestination = if(mainViewModel.isSignedIn()) Screen.MAIN.name else Screen.LOGIN.name

    Log.d("NAMASTE", mainViewModel.isSignedIn().toString())

    NavHost(navController = navController, startDestination = startDestination) {

        composable(route = Screen.LOGIN.name){
            Column {
                LoginScreen(mainViewModel){
                    mainViewModel.fetchNotes()
                    navController.navigate(Screen.MAIN.name)
                }
            }
        }

        composable(
            route = Screen.MAIN.name,
            popEnterTransition = { scaleIn(
                animationSpec = tween(200),
                initialScale = 0.2f,
                transformOrigin = TransformOrigin(0.5f, 0f)
            ) + fadeIn(animationSpec = tween(200)) },
        ){
            MainScreen(
                viewModel = mainViewModel,
                createNewNote = {
                    mainViewModel.getNoteById()
                    navController.navigate(Screen.EDIT.name)
                },
                navigateToEditScreen = { navController.navigate(Screen.EDIT.name) },
                navigateToLoginScreen = {
                    if(startDestination == Screen.LOGIN.name){
                        navController.popBackStack()
                    }else{
                        navController.navigate(Screen.LOGIN.name)
                    }
                }
            )
        }

        composable(
            route = Screen.EDIT.name,
            enterTransition = { scaleIn(
                animationSpec = tween(200),
                initialScale = 0.2f,
            ) + fadeIn(animationSpec = tween(200)) },
            exitTransition = { scaleOut(
                animationSpec = tween(200),
                targetScale = 0.2f,
            ) + fadeOut(animationSpec = tween(200)) },
            popExitTransition = { scaleOut(
                animationSpec = tween(200),
                targetScale = 0.2f,
                transformOrigin = TransformOrigin(0.5f, 1f)
            ) + fadeOut(animationSpec = tween(200)) }
        ){
            NotesEditScreen(mainViewModel){
                navController.popBackStack()
            }
        }

    }
}