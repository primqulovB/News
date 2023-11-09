package uz.gita.newsapp.domain.use_case.impl

import uz.gita.newsapp.domain.repository.AppRepository
import uz.gita.newsapp.domain.use_case.PostUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostUseCaseImpl @Inject constructor(
    private val appRepository: AppRepository
) : PostUseCase {
    override suspend fun invoke() {

    }
}