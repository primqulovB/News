package uz.gita.newsapp.data.sources.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.gita.newsapp.data.model.RecentData
import uz.gita.newsapp.data.sources.local.room.entity.RecentEntity

@Dao
interface RecentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recentEntity: RecentEntity)

    @Query("SELECT * FROM recent_words ORDER BY id DESC")
    suspend fun getAllRecent(): List<RecentEntity>
}