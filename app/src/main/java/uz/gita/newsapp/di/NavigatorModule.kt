package uz.gita.newsapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.newsapp.navigator.AppNavigator
import uz.gita.newsapp.navigator.NavigationDispatcher
import uz.gita.newsapp.navigator.NavigationHandler

@Module
@InstallIn(SingletonComponent::class)
interface NavigatorModule {
    @Binds
    fun bindAppNavigator(impl: NavigationDispatcher): AppNavigator

    @Binds
    fun bindNavigationHandler(impl: NavigationDispatcher): NavigationHandler
}