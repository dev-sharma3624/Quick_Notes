package com.example.quicknotes.ui.screen

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quicknotes.R
import com.example.quicknotes.data.data_classes.Note
import com.example.quicknotes.ui.theme.QuickNotesTheme
import com.example.quicknotes.ui.theme.bgColor
import com.example.quicknotes.ui.theme.primaryColor
import com.example.quicknotes.viewModel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
//            NotesEditScreen()
        }
    }
}

@Composable
fun NotesEditScreen(
    viewModel: MainViewModel,
    navigateToMainScreen : () -> Unit
) {

    val note = viewModel.individualNote

    val datePattern = if(DateFormat.is24HourFormat(LocalContext.current)) "dd MMM yyyy HH:mm" else "dd MMM yyyy hh:mm"
    val dateFormatter = SimpleDateFormat(datePattern, Locale.getDefault())

    val textFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
    )

    Box(
        contentAlignment = Alignment.BottomEnd
    ) {
        LazyColumn(
            modifier = Modifier.padding(12.dp)
                .fillMaxSize()
                .background(bgColor, RoundedCornerShape(10))
                .border(1.dp, Color.Black, RoundedCornerShape(10))
                .clip(RoundedCornerShape(10))
        ){
            item {
                TextField(
                    onValueChange = {
                        note.value = Note(
                            id = note.value!!.id,
                            title = it,
                            lastUpdated = System.currentTimeMillis(),
                            content = note.value!!.content
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Title",
                            style = TextStyle(
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                            modifier = Modifier.alpha(0.6f)
                        )
                    },
                    value = note.value!!.title,
                    textStyle = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    colors = textFieldColors,
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 48.dp, start = 16.dp, end = 16.dp)
                )

                Text(
                    text = dateFormatter.format(Date(note.value!!.lastUpdated)),
                    fontSize = 16.sp,
                    color = Color(0f, 0f, 0f, 0.29f),
                    modifier = Modifier.padding(top = 16.dp, bottom = 12.dp, start = 36.dp, end = 16.dp)
                        .fillMaxWidth()
                )

                TextField(
                    onValueChange = {
                        note.value = Note(
                            id = note.value!!.id,
                            title = note.value!!.title,
                            lastUpdated = System.currentTimeMillis(),
                            content = it
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Content",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Light,
                                lineHeight = 26.sp
                            ),
                            modifier = Modifier.alpha(0.6f)
                        )
                    },
                    value = note.value!!.content,
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        lineHeight = 26.sp
                    ),
                    colors = textFieldColors,
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 4.dp)
                )
            }

        }

        Box(
            modifier = Modifier.padding(48.dp)
        ) {

            IconButton(
                onClick = {
                    viewModel.saveNote()
                    navigateToMainScreen()
                },
                modifier = Modifier.clip(CircleShape)
                    .background(primaryColor)
                    .size(64.dp)

            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }



}
