package com.example.facerecognition.di

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.facerecognition.data.converter.DataConverter
import com.example.facerecognition.data.converter.csv.DataConverterCSV
import com.example.facerecognition.data.file.AndroidInternalStorageFileWriter
import com.example.facerecognition.data.file.FileWriter
import com.example.facerecognition.data.repository.ExportRepositoryImpl
import com.example.facerecognition.domain.repository.ExportRepository
import com.example.facerecognition.presentation.MyViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
object VMModule {

    @Provides
    @ViewModelScoped
    fun provideVM(repository: ExportRepository): ViewModel {
        return MyViewModel(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideExportRepository(
        fileWriter: FileWriter,
        dataConverter: DataConverter
    ): ExportRepository {
        return ExportRepositoryImpl(fileWriter, dataConverter)
    }

    @Provides
    @ViewModelScoped
    fun provideDataConverter(): DataConverter {
        return DataConverterCSV()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFileWrite(context: Application): FileWriter {
        return AndroidInternalStorageFileWriter(context)
    }

}

