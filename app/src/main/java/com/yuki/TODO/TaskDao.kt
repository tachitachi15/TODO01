package com.yuki.TODO

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TaskDao{
    @Query("SELECT * FROM task ORDER BY importance DESC") //ORDER BY 並び替えの基準となる要素（複数指定OK)　昇順ASC　降順DESC
    fun getAll(): Single<List<Task>> //処理成功でonSuccess 失敗でonErrorが呼び出し元で呼ばれる

    @Insert
    fun insert(task: Task) : Completable //Completable 処理成功でonSuccess 失敗でonError

    @Update
    fun update(task: Task) : Completable

    @Delete
    fun delete(task: Task) : Completable
}

