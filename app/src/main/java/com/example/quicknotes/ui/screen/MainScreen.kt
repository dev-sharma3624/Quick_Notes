package com.example.quicknotes.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quicknotes.R
import com.example.quicknotes.ui.theme.QuickNotesTheme

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
            MainScreen()
        }
    }
}

@Composable
fun MainScreen(){

    val bgColor = Color(0xFFF1F1F1)

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
            )

            Text(
                text = "Your Notes",
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            items(12){

                ElevatedCard(
                    onClick = {},
                    elevation = CardDefaults.cardElevation(0.dp),
                    colors = CardColors(
                        containerColor = Color.White,
                        contentColor = Color.Unspecified,
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color.Unspecified
                    ),
                    modifier = Modifier.padding(8.dp),
                ){
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = "Title ldfjdk dlflkdf  lkdfdjlf  lkgkjgk dfkjdhfkfd",
                            fontSize = 24.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Text(
                            text = "02 Aug 2025 14:15",
                            fontSize = 16.sp,
                            color = Color(0f, 0f, 0f, 0.29f)
                        )
                        Text(
                            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus consequat suscipit risus, ut egestas erat pulvinar maximus. Suspendisse sapien est,  dlfk llk",
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