package com.example.quicknotes.ui.screen

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quicknotes.R
import com.example.quicknotes.ui.theme.QuickNotesTheme
import com.example.quicknotes.ui.theme.bgColor
import com.example.quicknotes.ui.theme.primaryColor
import com.example.quicknotes.viewModel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){
    QuickNotesTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(R.drawable.bg),
                    contentScale = ContentScale.FillBounds
                )
        ) {
//            MainScreen(Main{}, {})
        }
    }
}

@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel(),
    navigateToEditScreen : () -> Unit,
    navigateToLoginScreen : () -> Unit,
    createNewNote : () -> Unit
){

    val notes by viewModel.notes.collectAsState()

    val datePattern = if(DateFormat.is24HourFormat(LocalContext.current)) "dd MMM yyyy HH:mm" else "dd MMM yyyy hh:mm"
    val dateFormatter = SimpleDateFormat(datePattern, Locale.getDefault())

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {

            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(0.6f),
                drawerContainerColor = Color.White
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(1f)
                        .padding(vertical = 36.dp, horizontal = 12.dp)
                ) {

                    Icon(
                        painter = painterResource(R.drawable.user_avatar),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.scale(0.75f)
                    )

                    Text(
                        text = viewModel.getUserName() ?: "Username"
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 24.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 4.dp)
                            .clickable {
                                viewModel.signOut()
                                navigateToLoginScreen()
                            },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Sign Out"
                        )

                        Icon(
                            painter = painterResource(R.drawable.logout),
                            contentDescription = null
                        )
                    }

                }
            }


        },
        drawerState = drawerState
    ) {


        Column(
            modifier = Modifier.padding(12.dp)
                .fillMaxSize()
                .background(bgColor, RoundedCornerShape(10))
                .border(1.dp, Color.Black, RoundedCornerShape(10))
                .padding(top = 24.dp, start = 8.dp, end = 8.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(R.drawable.user_avatar),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.scale(0.75f)
                        .clickable {
                            if(drawerState.isClosed){
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            }
                        }
                )

                Text(
                    text = "Your Notes",
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.BottomEnd
            ) {

                when{

                    notes == null ->
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                        }

                    notes!!.isEmpty() -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No notes created",
                                fontSize = 20.sp
                            )
                        }
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier.padding(top = 16.dp)
                                .fillMaxSize()
                        ) {
                            items(notes!!, key = {it.id}){

                                Card(
                                    onClick = {
                                        viewModel.setNoteId(it.id)
                                        navigateToEditScreen()
                                    },
                                    elevation = CardDefaults.cardElevation(0.dp),
                                    colors = CardColors(
                                        containerColor = Color.White,
                                        contentColor = Color.Unspecified,
                                        disabledContainerColor = Color.White,
                                        disabledContentColor = Color.Unspecified
                                    ),
                                    modifier = Modifier.padding(8.dp)
                                        .fillMaxWidth(),
                                ){
                                    Column(
                                        modifier = Modifier.padding(12.dp)
                                    ) {
                                        Text(
                                            text = it.title,
                                            fontSize = 24.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(vertical = 4.dp)
                                        )
                                        Text(
                                            text = dateFormatter.format(Date(it.lastUpdated)),
                                            fontSize = 16.sp,
                                            color = Color(0f, 0f, 0f, 0.29f)
                                        )
                                        Text(
                                            text = it.content,
                                            fontSize = 16.sp,
                                            maxLines = 4,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(vertical = 4.dp)
                                        )
                                    }

                                }

                            }
                        }
                    }

                }

                Box(
                    modifier = Modifier.padding(48.dp)
                ) {

                    IconButton(
                        onClick = {
                            createNewNote()
                        },
                        modifier = Modifier.clip(CircleShape)
                            .background(primaryColor)
                            .size(64.dp)

                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }


            }



        }

    }




}