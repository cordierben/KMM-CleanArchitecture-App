package com.example.kmm_firstapp.android.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmm_firstapp.domain.note.Note
import com.example.kmm_firstapp.domain.note.NoteDataSource
import com.example.kmm_firstapp.presentation.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailviewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val noteTitle = savedStateHandle.getStateFlow("noteTitle","")
    private val isNoteTitleFocused = savedStateHandle.getStateFlow("isNoteTitleFocused",false)
    private val noteContent = savedStateHandle.getStateFlow("noteContent","")
    private val isNoteContentFocused = savedStateHandle.getStateFlow("isNoteContentFocused",false)
    private val noteColor = savedStateHandle.getStateFlow("noteColor",Note.generateRandomColor())

    val state = combine(
        noteTitle,isNoteTitleFocused,noteContent,isNoteContentFocused,noteColor
    ){ title, isTitleFocused, content, isContentFocused, color ->
        NoteDetailState(
            title,
            title.isEmpty() && isTitleFocused,
            content,
            content.isEmpty() && isContentFocused,
            color.toLong()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailState())

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>("noteId")?.let { existingNoteId ->
            if(existingNoteId == -1L){
                return@let
            }
            this.existingNoteId = existingNoteId
            viewModelScope.launch {
                noteDataSource.getNoteById(existingNoteId)?.let {
                    savedStateHandle["noteTitle"] = it.title
                    savedStateHandle["noteContent"] = it.content
                    savedStateHandle["noteColor"] = it.colorHex
                }
            }
        }
    }

    fun onNoteTitleChanged(text: String){
        savedStateHandle["noteTitle"] = text
    }

    fun onNoteContentChanged(text: String){
        savedStateHandle["noteContent"] = text
    }

    fun onNoteTitleFocusChanged(isFocused: Boolean){
        savedStateHandle["isNoteTitleFocused"] = isFocused
    }

    fun onNoteContentFocusChanged(isFocused: Boolean){
        savedStateHandle["isNoteContentFocused"] = isFocused
    }

    fun saveNote(){
        viewModelScope.launch {
            noteDataSource.insertNote(
                Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value,
                    created = DateTimeUtil.now()
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }
}