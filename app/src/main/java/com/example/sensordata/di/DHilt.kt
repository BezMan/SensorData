package com.example.sensordata.di

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.sensordata.data.file.FileWriterImpl
import com.example.sensordata.data.file.IFileWriter
import com.example.sensordata.data.repository.RepositoryImpl
import com.example.sensordata.domain.repository.IRepository
import com.example.sensordata.presentation.MyViewModel
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
    fun provideVM(repository: IRepository): ViewModel {
        return MyViewModel(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideExportRepository(
        fileWriter: IFileWriter,
    ): IRepository {
        return RepositoryImpl(fileWriter)
    }

}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFileWrite(context: Application): IFileWriter {
        return FileWriterImpl(context)
    }

}

