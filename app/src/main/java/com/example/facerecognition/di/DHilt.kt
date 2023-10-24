package com.example.facerecognition.di

import androidx.lifecycle.ViewModel
import com.example.facerecognition.data.repository.CsvFileHandler
import com.example.facerecognition.data.repository.IFileHandler
import com.example.facerecognition.data.repository.Repository
import com.example.facerecognition.presentation.MyViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object VMModule {

    @Provides
    @ViewModelScoped
    fun provideVM(repository: Repository): ViewModel {
        return MyViewModel(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideVideoRepository(fileHandler: IFileHandler): Repository {
        return Repository(fileHandler)
    }

    @Provides
    @ViewModelScoped
    fun provideFileHandler(): IFileHandler {
        return CsvFileHandler()
    }

}

