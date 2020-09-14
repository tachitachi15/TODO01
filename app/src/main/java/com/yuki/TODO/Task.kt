package com.yuki.TODO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "state")
    var state: Int,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "importance")
    var importance: Float?
)