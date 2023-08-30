package com.transact.assessment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.transact.assessment.data.local.dao.FilterDAO
import com.transact.assessment.data.local.dao.ImageInfoDao
import com.transact.assessment.data.local.dao.RemoteKeysDao
import com.transact.assessment.data.local.entity.FilterEntity
import com.transact.assessment.data.local.entity.ImageInfoEntity
import com.transact.assessment.data.local.entity.RemoteKeysEntity

@Database(
    entities = [ImageInfoEntity::class, FilterEntity::class, RemoteKeysEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ImageDatabase: RoomDatabase() {
    abstract fun imageInfoDAO(): ImageInfoDao

    abstract fun filterDAO(): FilterDAO

    abstract fun remoteKeysDao(): RemoteKeysDao

}