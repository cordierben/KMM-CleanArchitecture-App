package com.example.kmm_firstapp.android.note_list

import com.example.kmm_firstapp.domain.note.Note

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
)