package com.example.myplay1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameScreen()
        }
    }
}

@Composable
fun GameScreen() {
    var currentRound by remember { mutableIntStateOf(1) }
    var targetValue by remember { mutableIntStateOf(Random.nextInt(1, 51)) }
    var sliderValue by remember { mutableFloatStateOf(25f) }
    var score by remember { mutableIntStateOf(0) }
    var showResult by remember { mutableStateOf(false) }
    val rounds = 5

    if (showResult) {
        ResultScreen(score) {
            score = 0
            currentRound = 1
            targetValue = Random.nextInt(1, 51)
            sliderValue = 25f
            showResult = false
        }
    } else {
        MaterialTheme {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Round: $currentRound/$rounds", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Target: $targetValue", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(32.dp))

                Slider(
                    value = sliderValue,
                    onValueChange = { sliderValue = it },
                    valueRange = 1f..50f,
                    steps = 48
                )

                Spacer(modifier = Modifier.height(16.dp))
                val sliderValueInt = sliderValue.toInt()
                Text("Selected: $sliderValueInt", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(32.dp))

                Button(onClick = {
                    val roundScore = calculateScore(targetValue, sliderValueInt)
                    score += roundScore
                    if (currentRound < rounds) {
                        currentRound++
                        targetValue = Random.nextInt(1, 51)
                        sliderValue = 25f
                    } else {
                        showResult = true
                    }
                }) {
                    Text("Confirm")
                }
            }
        }
    }
}

@Composable
fun ResultScreen(score: Int, onRestart: () -> Unit) {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Your Score: $score", style = MaterialTheme.typography.displayLarge)
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onRestart) {
                Text("Play Again")
            }
        }
    }
}

private fun calculateScore(target: Int, selected: Int): Int {
    val difference = abs(target - selected)
    return if (difference <= 9) 10 - difference else 0
}