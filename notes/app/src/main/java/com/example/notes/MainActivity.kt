package com.example.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.notes.ui.theme.NotesTheme
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first
import android.content.Context

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "note_data")
private const val NOTE_KEY = "note"


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesTheme {
                NoteApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteApp(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current
) {
    var noteText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            noteText = context.dataStore.data.map { preferences ->
                preferences[stringPreferencesKey(NOTE_KEY)] ?: ""
            }.first()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Note App") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = noteText,
                onValueChange = { newText ->
                    noteText = newText
                    saveNote(context, newText, coroutineScope)
                },
                label = { Text("Enter your note") },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

private fun saveNote(context: Context, text: String, scope: CoroutineScope) = scope.launch {
    context.dataStore.edit { preferences ->
        preferences[stringPreferencesKey(NOTE_KEY)] = text
    }
}
