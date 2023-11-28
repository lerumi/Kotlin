package com.example.dz2

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.EventLogTags.Description
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTarget
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.createBitmap
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.dz2.retrofit.GifApi
import com.example.dz2.retrofit.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import kotlinx.coroutines.*
import retrofit2.http.GET
import retrofit2.http.Path
import kotlin.random.Random
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConfigChange()
        }
    }
}

const val x1 = 100
const val x2 = 100
var d = 0
var list = mutableListOf<String>()
val forColorRed = Color(0xFFF44336)
val forColorBlue = Color(0xFF2D4DC2)


val gifApi = GetImageByApi()
fun GetImageByApi():GifApi{
    val retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val gifApi = retrofit.create(GifApi::class.java)
    return gifApi
}


@Composable
fun PaintImage(url:String)

{
    val context = LocalContext.current
        Box(modifier = Modifier.size(100.dp, 100.dp ).clickable(onClick = {
            val intent = Intent(context, Pribliz::class.java)
            intent.putExtra("key", url)
            context.startActivity(intent)}))
        {
            AsyncImage(model = url, contentDescription = "")

        }


}
@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)

fun ConfigChange() {
    val amount_column = listOf("Horizontal" to 4, "Vertical" to 3)
    val configuration = LocalConfiguration.current
    var count = rememberSaveable { mutableListOf<String>() }
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            B(amount_column[0].second, count)
        }

        else -> {
            B(amount_column[1].second, count)
        }
    }
}
suspend fun RepeatLoad(count: MutableList<String>)
{
    repeat(5)
    {
        val str = gifApi.getWifuByID((1..15).random())
        count.add(str.images[0])
    }
}
@Composable
fun B(indicator: Int, count: MutableList<String>) {
    var c = 0
    d = (count.size)
    var progress by remember { mutableStateOf("work") }
    var isReverse by remember { mutableStateOf(0) }
    if(progress=="work" || progress=="load") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            if(isReverse == 1){
                count.reverse()
                isReverse=0
            }
            while (d >= indicator) {
                Row {
                    for (i in 0..(indicator - 1)) {

                        PaintImage(count[i + c])
                    }
                }
                c += indicator
                d -= indicator
            }
            Row {
                while (0 < d && d < indicator) {
                    PaintImage(count[c])
                    c += 1
                    d -= 1
                }
            }

            val scope = rememberCoroutineScope()
            Button(onClick = {isReverse = 1 })
            {
                Text(text="reverse")
            }
            Button(
                onClick = {
                   scope.launch {
                        progress = "load"
                        try {
                            RepeatLoad(count)

                        } catch (e: Exception) {
                            // This will catch any exception, because they are all descended from Exception
                            progress = "Error"
                        }

                        delay(1000)
                        progress = "work"
                    }
                }
            ) {
                Text("Запустить", fontSize = 22.sp)
            }


        }

    }
    if(progress=="load")
    {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
                 contentAlignment = Alignment.Center) {
            CircularProgressIndicator( )
        }
    }
    if(progress=="Error")
    {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
            contentAlignment = Alignment.Center) {
            Button(onClick = { progress="work" }) {
                Text(text = "povtori")
            }
        }
    }

}