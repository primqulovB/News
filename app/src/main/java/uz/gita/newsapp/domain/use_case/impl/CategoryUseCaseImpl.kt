package uz.gita.newsapp.domain.use_case.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.newsapp.data.model.CategoryData
import uz.gita.newsapp.domain.repository.AppRepository
import uz.gita.newsapp.domain.use_case.CategoryUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryUseCaseImpl @Inject constructor(
    private val appRepository: AppRepository
) : CategoryUseCase {
    override fun invoke() : Flow<Result<List<CategoryData>>> = appRepository.getCategories()
}