package uz.gita.newsapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.newsapp.data.sources.local.room.RoomDB
import uz.gita.newsapp.data.sources.local.room.dao.RecentDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @[Provides Singleton]
    fun provideRoom(@ApplicationContext context: Context): RoomDB =
        Room.databaseBuilder(
            context,
            RoomDB::class.java,
            "Recents.DB"
        ).build()

    @Provides
    fun provideRecentDao(roomDB: RoomDB): RecentDao = roomDB.getRecentDao()
}