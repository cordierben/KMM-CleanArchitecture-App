package com.example.kmm_firstapp.android.di

import android.app.Application
import com.example.kmm_firstapp.data.local.DatabaseDriverFactory
import com.example.kmm_firstapp.data.note.SqlDelightNoteDataSource
import com.example.kmm_firstapp.database.NoteDatabase
import com.example.kmm_firstapp.domain.note.NoteDataSource
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDrive(app: Application): SqlDriver{
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideNoteDatasource(driver: SqlDriver): NoteDataSource{
        return SqlDelightNoteDataSource(NoteDatabase(driver))
    }
}