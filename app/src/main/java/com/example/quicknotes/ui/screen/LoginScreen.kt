package com.example.quicknotes.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quicknotes.R
import com.example.quicknotes.ui.theme.QuickNotesTheme

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
            LoginScreen()
        }
    }
}

@Composable
fun LoginScreen(){

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
                text = "Login",
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
                value = "",
                onValueChange = {},
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
                value = "",
                onValueChange = {},
                modifier = internalComponentsModifier
                    .border(1.dp, Color.Black, RoundedCornerShape(50)),
                colors = textFieldColors
            )

            Button(
                onClick = {},
                modifier = internalComponentsModifier
                    .fillMaxWidth(),
                shape = RectangleShape
            ) {
                Text(
                    text = "Sign In",
                    fontSize = fontSize_TexFieldHeadings_buttonText,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Button(
                onClick = {},
                modifier = internalComponentsModifier
                    .fillMaxWidth(),
                shape = RectangleShape
            ) { }

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

        }

    }

}