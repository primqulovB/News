package uz.gita.newsapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.newsapp.domain.use_case.CategoryUseCase
import uz.gita.newsapp.domain.use_case.PostUseCase
import uz.gita.newsapp.domain.use_case.RecentWordsUseCase
import uz.gita.newsapp.domain.use_case.impl.CategoryUseCaseImpl
import uz.gita.newsapp.domain.use_case.impl.PostUseCaseImpl
import uz.gita.newsapp.domain.use_case.impl.RecentWordsUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {
    @Binds
    fun bindCategoryUseCase(impl: CategoryUseCaseImpl): CategoryUseCase

    @Binds
    fun bindPostUseCase(impl: PostUseCaseImpl): PostUseCase

    @Binds
    fun bindRecentWordsUseCase(impl: RecentWordsUseCaseImpl): RecentWordsUseCase
}