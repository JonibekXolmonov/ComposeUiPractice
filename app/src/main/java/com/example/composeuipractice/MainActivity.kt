package com.example.composeuipractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.composeuipractice.data.Comment
import com.example.composeuipractice.data.Stadium
import com.example.composeuipractice.stadium.StadiumDescriptionScreen
import com.example.composeuipractice.ui.theme.ComposeUiPracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ComposeUiPracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    StadiumDescriptionScreen(
                        stadium = Stadium(),
                        comments = listOf(
                            Comment(comment = R.string.comment),
                            Comment(comment = R.string.comment),
                            Comment(comment = R.string.comment),
                            Comment(comment = R.string.comment),
                            Comment(comment = R.string.comment)
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeUiPracticeTheme {

    }
}