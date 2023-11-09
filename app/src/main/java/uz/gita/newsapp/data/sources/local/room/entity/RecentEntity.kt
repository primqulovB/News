package uz.gita.newsapp.data.sources.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_words")
data class RecentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val word: String
)