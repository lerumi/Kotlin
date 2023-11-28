package com.example.dz2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


class Pribliz : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val number = intent.getStringExtra("key")
        super.onCreate(savedInstanceState)
        setContent {
            ConfigChange1(number)
        }
    }
}

@Composable
fun ConfigChange1(intent:String?)
{
    val context = LocalContext.current
    Box(modifier = Modifier.size(100.dp, 100.dp ).clickable {
        (context as? Activity)?.finish()
    })
    {
        AsyncImage(model = intent.toString(), contentDescription = "")

    }



}