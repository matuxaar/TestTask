package com.example.testtask.di.modules

import androidx.lifecycle.ViewModel
import com.example.testtask.di.viewmodel.ViewModelKey
import com.example.testtask.ui.camera.CameraViewModel
import com.example.testtask.ui.checkauth.CheckAuthViewModel
import com.example.testtask.ui.login.LogInViewModel
import com.example.testtask.ui.photos.ImageListViewModel
import com.example.testtask.ui.register.RegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ImageListViewModel::class)
    fun bindImageListViewModel(viewModel: ImageListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LogInViewModel::class)
    fun bindLogInViewModel(viewModel: LogInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    fun bindRegisterViewModel(viewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CheckAuthViewModel::class)
    fun bindCheckAuthViewModel(viewModel: CheckAuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CameraViewModel::class)
    fun bindCameraViewModel(viewModel: CameraViewModel): ViewModel
}