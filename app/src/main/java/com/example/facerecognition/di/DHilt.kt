package com.example.facerecognition.di

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.facerecognition.data.converter.IDataConverter
import com.example.facerecognition.data.converter.csv.DataConverterCSVImpl
import com.example.facerecognition.data.file.FileWriterImpl
import com.example.facerecognition.data.file.IFileWriter
import com.example.facerecognition.data.repository.RepositoryImpl
import com.example.facerecognition.domain.repository.IRepository
import com.example.facerecognition.presentation.MyViewModel
import com.example.facerecognition.utils.PermissionManager
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
    fun provideVM(repository: IRepository, permissionManager: PermissionManager): ViewModel {
        return MyViewModel(repository, permissionManager)
    }

    @Provides
    @ViewModelScoped
    fun provideExportRepository(
        fileWriter: IFileWriter,
        dataConverter: IDataConverter
    ): IRepository {
        return RepositoryImpl(fileWriter, dataConverter)
    }

    @Provides
    @ViewModelScoped
    fun providePermissionManager(context: Application): PermissionManager{
        return PermissionManager(context)
    }


        @Provides
    @ViewModelScoped
    fun provideDataConverter(): IDataConverter {
        return DataConverterCSVImpl()
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

