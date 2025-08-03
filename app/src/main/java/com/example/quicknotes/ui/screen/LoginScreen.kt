package com.example.quicknotes.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quicknotes.R
import com.example.quicknotes.ui.theme.QuickNotesTheme
import com.example.quicknotes.ui.theme.primaryColor
import com.example.quicknotes.viewModel.AuthenticationVm
import org.koin.androidx.compose.koinViewModel

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview(){
    QuickNotesTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(R.drawable.bg),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            LoginScreen(){}
        }
    }
}

@Composable
fun LoginScreen(
    vm : AuthenticationVm = koinViewModel(),
    navigateToMainScreen: () -> Unit
){

    val context = LocalContext.current
    val toast : (String) -> Unit = {Toast.makeText(context, it, Toast.LENGTH_SHORT).show()}

    val fontSize_TexFieldHeadings_buttonText = 24.sp
    val internalComponentsModifier = Modifier.padding(8.dp)
    val textFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
    )

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Welcome!",
            fontSize = 48.sp,
            modifier = Modifier.background(Color.White)
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .padding(24.dp)
    ){

        Column(
            modifier = Modifier.background(Color.White)
                .border(1.dp, Color.Black, RoundedCornerShape(10))
                .padding(vertical = 24.dp, horizontal = 16.dp)
        ) {

            Text(
                text = vm.currentTab.value,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Email",
                fontSize = fontSize_TexFieldHeadings_buttonText,
                modifier = internalComponentsModifier
            )

            TextField(
                value = vm.emailField.value,
                onValueChange = {vm.setEmailField(it)},
                textStyle = TextStyle(
                    fontSize = 20.sp
                ),
                placeholder = { Text("Enter email") },
                modifier = internalComponentsModifier
                    .border(1.dp, Color.Black, RoundedCornerShape(50)),
                colors = textFieldColors
            )

            Text(
                text = "Password",
                fontSize = fontSize_TexFieldHeadings_buttonText,
                modifier = internalComponentsModifier
            )

            TextField(
                value = vm.pwdField.value,
                onValueChange = {vm.setPwdField(it)},
                textStyle = TextStyle(
                    fontSize = 20.sp
                ),
                placeholder = { Text("Enter password") },
                visualTransformation = if(vm.showPwd.value) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = internalComponentsModifier
                    .border(1.dp, Color.Black, RoundedCornerShape(50)),
                colors = textFieldColors
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = vm.showPwd.value,
                    onCheckedChange = {vm.setShowPwd()}
                )
                Text(
                    text = "Show Password"
                )
            }

            Button(
                onClick = {vm.onClickButton(){result, message ->
                    vm.setIsEmailProgressActive()
                    if(result){
                        toast("${vm.currentTab.value} Successful")
                        navigateToMainScreen()
                    }
                    else{
                        Log.e("Authentication Error", message!!)
                    }
                } },
                modifier = internalComponentsModifier
                    .fillMaxWidth(),
                shape = RectangleShape,
                colors = ButtonColors(
                    containerColor = primaryColor,
                    contentColor = Color.White,
                    disabledContainerColor = primaryColor,
                    disabledContentColor = Color.White
                ),
                enabled = !vm.isEmailProgressActive.value
            ) {

                if(vm.isEmailProgressActive.value){
                    CircularProgressIndicator(
                        color = Color.White
                    )
                }else{
                    Text(
                        text = vm.currentTab.value,
                        fontSize = fontSize_TexFieldHeadings_buttonText,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

            }

            Image(
                painter = painterResource(R.drawable.android_light_sq_ctn),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable {
                        vm.signInGoogle(context){result, message ->
                            if(result){
                                toast("${vm.currentTab.value} Successful")
                                navigateToMainScreen()
                            }
                            else{
                                Log.e("Authentication Error", message!!)
                            }
                        }
                    }
            )


            Row(
                modifier = internalComponentsModifier.fillMaxWidth()
                    .clickable {
                        vm.setCurrentTab()
                    },
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if(vm.currentTab.value == "Sign In") "Don't have an account?" else "Already have an account?"
                )
                Text(
                    text = if(vm.currentTab.value == "Sign In") "Sign Up" else "Sign In",
                    color = Color.Blue
                )
            }

            vm.errorMessage.value?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.Red
                )
            }


            Spacer(modifier = Modifier.padding(vertical = 16.dp))


        }

    }

}
