package com.example.quicknotes.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quicknotes.R
import com.example.quicknotes.ui.theme.QuickNotesTheme
import com.example.quicknotes.ui.theme.bgColor

@Preview(showBackground = true)
@Composable
fun NotesEditScreenPreview(){
    QuickNotesTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(R.drawable.bg),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            NotesEditScreen()
        }
    }
}

@Composable
fun NotesEditScreen() {

    LazyColumn(
        modifier = Modifier.padding(12.dp)
            .fillMaxSize()
            .background(bgColor, RoundedCornerShape(10))
            .border(1.dp, Color.Black, RoundedCornerShape(10))
            .clip(RoundedCornerShape(10))
    ){
        item {
            BasicTextField(
                onValueChange = {},
                value = "Title ldfjdk dlflkdf  lkdfdjlf  l",
                textStyle = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 48.dp, start = 16.dp, end = 16.dp)
            )

            Text(
                text = "02 Aug 2025 14:15",
                fontSize = 16.sp,
                color = Color(0f, 0f, 0f, 0.29f),
                modifier = Modifier.padding(top = 16.dp, bottom = 12.dp, start = 16.dp, end = 16.dp)
            )

            BasicTextField(
                onValueChange = {},
                value = "Lorem ipsum dolor sit amet, consectetur adipis  cing elit. Phasellus consequat suscipit risus, ut egestas erat pulvinar maximus. Suspendisse sapien est, vestibulum nec arcu eget, sollicitudin blandit mi. Nulla sed malesuada urna. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Donec non iaculis eros. Cras iaculis neque id augue maximus, eu dictum tortor pretium. Sed eget dui odio. Integer egestas nisl sed ornare commodo. Nulla ut interdum lacus. Praesent vel tincidunt velit, non tristique elit. Suspendisse auctor ante ut congue varius. Ut cursus porta interdum. Mauris ac est sit amet nisi sagittis suscipit. Maecenas at felis vitae massa venenatis efficitur sit amet vel massa.Lorem ipsum dolor sit amet, consectetur adipis  cing elit. Phasellus consequat suscipit risus, ut egestas erat pulvinar maximus. Suspendisse sapien est, vestibulum nec arcu eget, sollicitudin blandit mi. Nulla sed malesuada urna. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Donec non iaculis eros. Cras iaculis neque id augue maximus, eu dictum tortor pretium. Sed eget dui odio. Integer egestas nisl sed ornare commodo. Nulla ut interdum lacus. Praesent vel tincidunt velit, non tristique elit. Suspendisse auctor ante ut congue varius. Ut cursus porta interdum. Mauris ac est sit amet nisi sagittis suscipit. Maecenas at felis vitae massa venenatis efficitur sit amet vel massa.",
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    lineHeight = 26.sp
                ),
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp)
            )
        }

    }

}
