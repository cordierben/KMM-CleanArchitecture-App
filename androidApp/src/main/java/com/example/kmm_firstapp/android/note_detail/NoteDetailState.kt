package com.example.kmm_firstapp.android.note_detail

data class NoteDetailState(
    val noteTitle: String = "",
    val isNoteTitleFocused : Boolean = false,
    val notecontent: String = "",
    val isNotecontentFocused: Boolean = false,
    val noteColor: Long = 0xFFFFFF
)
