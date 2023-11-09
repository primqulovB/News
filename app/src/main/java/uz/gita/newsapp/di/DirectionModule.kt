package uz.gita.newsapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.newsapp.presentation.screens.detail.DetailContract
import uz.gita.newsapp.presentation.screens.detail.DetailDirection
import uz.gita.newsapp.presentation.screens.main.MainContract
import uz.gita.newsapp.presentation.screens.main.MainDirection

@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {
    @Binds
    fun bindMainDirection(impl: MainDirection): MainContract.Direction

    @Binds
    fun bindDetailDirection(impl: DetailDirection): DetailContract.Direction
}