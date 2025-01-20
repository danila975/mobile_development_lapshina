// MainActivity.kt
package com.example.sputnik

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.sputnik.ui.theme.SputnikTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            setContent {
                SputnikTheme {
                    AppNavigation()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("MainActivity", "Ошибка при установке содержимого", e)
            Toast.makeText(this, "Произошла ошибка при запуске приложения", Toast.LENGTH_LONG).show()
        }
    }
}
