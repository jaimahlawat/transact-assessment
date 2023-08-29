package com.transact.assessment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.transact.assessment.data.local.dao.ImageInfoDao
import com.transact.assessment.data.local.entity.ImageInfoEntity

@Database(entities = [ImageInfoEntity::class], version = 1, exportSchema = false)
abstract class ImageDatabase: RoomDatabase() {
    abstract fun imageInfoDAO(): ImageInfoDao
}