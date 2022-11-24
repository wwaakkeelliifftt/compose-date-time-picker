package com.example.date_time_dialogs__compose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.date_time_dialogs__compose.connection_check.ConnectivityObserver
import com.example.date_time_dialogs__compose.connection_check.NetworkConnectivityObserver
import com.example.date_time_dialogs__compose.retrofit_up_act.FileViewModel
import kotlinx.coroutines.flow.collect
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class RetrofitUploadActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: FileViewModel = getViewModel()

            Surface() {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                            .height(400.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "Network status: ${viewModel.networkState}",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Normal
                        )
                    }

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column {
                            Button(onClick = {
                                val file = File(cacheDir, "myImage.jpg").apply {
                                    createNewFile()
                                    outputStream().use { fileOutputStream ->
                                        assets.open("wave.jpg").copyTo(fileOutputStream)
                                    }
                                }
                                viewModel.uploadImage(file = file)
                                Toast.makeText(applicationContext, "RE: vm=${viewModel.isUploaded}", Toast.LENGTH_SHORT).show()
                            }) {
                                Text(text = "Upload Image")
                            }
                            Text(
                                text = "FILE STATUS: ${viewModel.isUploaded}",
                                fontWeight = if (viewModel.isUploaded) FontWeight.SemiBold else FontWeight.Light
                            )
                        }
                }
            }

            }

        }

    }
}