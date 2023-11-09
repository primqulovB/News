package uz.gita.newsapp.domain.repository.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import uz.gita.newsapp.data.mapper.toData
import uz.gita.newsapp.data.model.CategoryData
import uz.gita.newsapp.data.model.PostData
import uz.gita.newsapp.data.model.RecentData
import uz.gita.newsapp.data.sources.local.room.dao.RecentDao
import uz.gita.newsapp.data.sources.local.room.entity.RecentEntity
import uz.gita.newsapp.data.sources.remote.retrofit.api.MainApi
import uz.gita.newsapp.domain.repository.AppRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val mainApi: MainApi,
    private val recentDao: RecentDao
) : AppRepository {
    override fun getCategories(): Flow<Result<List<CategoryData>>> = flow {
        val response = mainApi.getCategories()

        if (response.isSuccessful && response.body() != null) {
            val body = response.body()!!

            emit(Result.success(body[0].child.map { it.toData() }))
        } else {
            emit(Result.failure(IllegalArgumentException("error")))
        }
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)

    override fun getPostsByCategory(id: Int): Flow<Result<List<PostData>>> = flow {
        val response = mainApi.getPostsByCategory(id = id)

        if (response.isSuccessful && response.body() != null) {
            val body = response.body()!!

            emit(Result.success(body.map { it.toData() }))
        } else {
            emit(Result.failure(IllegalArgumentException("error")))
        }
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)

    override fun getPostsByCategoryAndWithSearch(
        id: Int,
        text: String
    ): Flow<Result<List<PostData>>> = flow {
        val response = mainApi.getPostsByCategory(id = id)

        if (response.isSuccessful && response.body() != null) {
            val body = response.body()!!

            emit(Result.success(body.map { it.toData() }
                .filter { it.title.lowercase().contains(text.lowercase()) }))
        } else {
            emit(Result.failure(IllegalArgumentException("error")))
        }
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)

    override suspend fun insertRecentWordsToRoom(text: String) = withContext(Dispatchers.IO) {
        recentDao.insert(RecentEntity(0, text))
    }

    override fun getAllRecent(): Flow<Result<List<RecentData>>> = flow {
        val data = recentDao.getAllRecent().map { it.toData() }

        if (data.size < 5) {
            emit(Result.success(data))
        } else {
            emit(Result.success(data.subList(0, 4)))
        }
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)
}