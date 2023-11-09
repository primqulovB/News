package uz.gita.newsapp.domain.use_case.impl

import uz.gita.newsapp.domain.repository.AppRepository
import uz.gita.newsapp.domain.use_case.RecentWordsUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecentWordsUseCaseImpl @Inject constructor(
    private val appRepository: AppRepository
) : RecentWordsUseCase {
    override suspend fun invoke() {

    }
}