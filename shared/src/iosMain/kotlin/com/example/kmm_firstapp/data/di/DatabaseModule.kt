package com.example.kmm_firstapp.data.di

import com.example.kmm_firstapp.data.local.DatabaseDriverFactory
import com.example.kmm_firstapp.data.note.SqlDelightNoteDataSource
import com.example.kmm_firstapp.database.NoteDatabase
import com.example.kmm_firstapp.domain.note.NoteDataSource

class DatabaseModule {

    private val factory by lazy { DatabaseDriverFactory() }
    val noteDatabaseSource: NoteDataSource by lazy {
        SqlDelightNoteDataSource(NoteDatabase(factory.createDriver()))
    }
}