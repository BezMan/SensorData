package com.example.facerecognition.di

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.impl.ImageCaptureConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CameraModule {

    @Provides
    fun provideCameraSelector(): CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    @Provides
    fun provideImageCapture(): ImageCapture {
        val imageCaptureConfig = ImageCaptureConfig.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()

        return ImageCapture(imageCaptureConfig)
    }
}

//@Module
//@InstallIn(ViewModelComponent::class)
//object VMModule {
//
//    @Provides
//    @ViewModelScoped
//    fun provideVideoRepository(apiService: YouTubeApiService): IRepository {
//        return VideoRepositoryImpl(apiService)
//    }
//
//    @Provides
//    @ViewModelScoped
//    fun provideVM(): ViewModel {
//        return MyViewModel()
//    }
//}

