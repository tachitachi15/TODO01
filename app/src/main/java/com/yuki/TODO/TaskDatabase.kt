package com.yuki.TODO

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object: Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        //カラム追加
        database.execSQL("ALTER TABLE Task ADD importance REAL")
    }
}

@Database(entities = [Task::class], version = 2)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        private var INSTANCE: TaskDatabase? = null

        private val lock = Any()
        //データベースが作られていないときのみ、新規作成されるようになってる
        fun getInstance(context: Context): TaskDatabase {
            synchronized(lock){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        TaskDatabase::class.java, "Task.db")
                        .addMigrations(MIGRATION_1_2) //データベースのcolumnを追加したときとかに必要
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}