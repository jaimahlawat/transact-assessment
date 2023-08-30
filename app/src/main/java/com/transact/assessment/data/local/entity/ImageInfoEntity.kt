package com.transact.assessment.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageInfoEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "author")
    val author: String? = null,
    @ColumnInfo(name = "width")
    val width: Int = 0,
    @ColumnInfo(name = "height")
    val height: Int = 0,
    @ColumnInfo(name = "url")
    val url: String? = null,
    @ColumnInfo(name = "downloadUrl")
    val downloadUrl: String? = null
)