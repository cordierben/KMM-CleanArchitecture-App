package com.example.kmm_firstapp.android.note_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun NoteDetailScreen(
    noteId: Long,
    navController: NavController,
    viewModel: NoteDetailviewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsState()
    val hasNoteBeenSaved by viewModel.hasNoteBeenSaved.collectAsState()

    LaunchedEffect(key1 = hasNoteBeenSaved, block = {
        if(hasNoteBeenSaved){
            navController.popBackStack()
        }
    })

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = viewModel::saveNote,
            backgroundColor = Color.Black
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Add Note",
                tint = Color.White
            )
        }
    }) { padding ->
        Column(
            modifier = Modifier
                .background(Color(state.noteColor))
                .fillMaxSize()
                .padding(padding)
        ) {
            TransparentHintTextField(
                text = state.noteTitle,
                hint = "Enter a title...",
                isHintVisible = state.isNoteTitleFocused,
                onValueChanged = viewModel::onNoteTitleChanged,
                onFocusChanged = { viewModel.onNoteTitleFocusChanged(it.isFocused) },
                singleLine = true,
                textStyle = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider(Modifier.height(1.dp).padding(horizontal = 16.dp).background(Color.LightGray))
            Spacer(modifier = Modifier.height(8.dp))
            TransparentHintTextField(
                text = state.notecontent,
                hint = "Enter a content...",
                isHintVisible = state.isNotecontentFocused,
                onValueChanged = viewModel::onNoteContentChanged,
                onFocusChanged = { viewModel.onNoteContentFocusChanged(it.isFocused) },
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier.weight(1f)
            )
        }
    }
}