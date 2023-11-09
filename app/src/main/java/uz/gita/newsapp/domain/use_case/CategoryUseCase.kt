package uz.gita.newsapp.domain.use_case

import kotlinx.coroutines.flow.Flow
import uz.gita.newsapp.data.model.CategoryData

interface CategoryUseCase {
    operator fun invoke() : Flow<Result<List<CategoryData>>>
}