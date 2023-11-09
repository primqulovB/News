package uz.gita.newsapp.data.sources.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.newsapp.data.sources.local.room.dao.RecentDao
import uz.gita.newsapp.data.sources.local.room.entity.RecentEntity

@Database(entities = [RecentEntity::class], version = 1)
abstract class RoomDB : RoomDatabase() {
    abstract fun getRecentDao(): RecentDao
}