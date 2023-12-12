package com.example.testtask.di

import android.content.Context
import com.example.testtask.MainActivity
import com.example.testtask.di.modules.DataBaseModule
import com.example.testtask.di.modules.DataModule
import com.example.testtask.di.modules.NetworkModule
import com.example.testtask.di.modules.SourceModule
import com.example.testtask.di.modules.ViewModelModule
import com.example.testtask.ui.camera.PreviewCameraActivity
import com.example.testtask.ui.checkauth.CheckAuthFragment
import com.example.testtask.ui.comments.CommentsFragment
import com.example.testtask.ui.enter.EnterFragment
import com.example.testtask.ui.login.LogInFragment
import com.example.testtask.ui.main.MainFragment
import com.example.testtask.ui.map.MapFragment
import com.example.testtask.ui.photos.ImageListFragment
import com.example.testtask.ui.register.RegisterFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DataBaseModule::class,
    DataModule::class,
    NetworkModule::class,
    ViewModelModule::class,
        SourceModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: ImageListFragment)
    fun inject(fragment: RegisterFragment)
    fun inject(fragment: LogInFragment)
    fun inject(fragment: MapFragment)
    fun inject(fragment: CommentsFragment)
    fun inject(fragment: EnterFragment)
    fun inject(fragment: CheckAuthFragment)
    fun inject(fragment: MainFragment)
    fun inject(activity: PreviewCameraActivity)
}