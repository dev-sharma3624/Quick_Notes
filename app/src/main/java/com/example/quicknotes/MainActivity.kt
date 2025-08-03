package com.example.quicknotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quicknotes.data.enum_classes.Screen
import com.example.quicknotes.ui.screen.LoginScreen
import com.example.quicknotes.ui.screen.MainScreen
import com.example.quicknotes.ui.screen.NotesEditScreen
import com.example.quicknotes.ui.theme.QuickNotesTheme
import com.example.quicknotes.viewModel.AuthenticationVm
import com.google.rpc.context.AttributeContext.Auth
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
    val authenticationVm : AuthenticationVm = koinViewModel()

    val startDestination = if(authenticationVm.isSignedIn()) Screen.MAIN.name else Screen.LOGIN.name

    NavHost(navController = navController, startDestination = startDestination) {

        composable(route = Screen.LOGIN.name){
            Column {
                LoginScreen(authenticationVm){
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
            MainScreen(){
                navController.navigate(Screen.EDIT.name)
            }
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
            NotesEditScreen()
        }

    }
}