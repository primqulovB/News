package uz.gita.newsapp.data.sources.remote.retrofit.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.gita.newsapp.data.sources.remote.retrofit.response.CategoryResponse
import uz.gita.newsapp.data.sources.remote.retrofit.response.PostResponse

interface MainApi {
    @GET("api.php?action=categories")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("api.php?action=posts")
    suspend fun getPostsByCategory(@Query("category") id: Int): Response<PostResponse>
}