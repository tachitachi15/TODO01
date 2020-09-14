package com.yuki.TODO

interface TaskContract {
    //presenterを利用する側
    interface View {
        fun onLoadTasks(tasks: List<Task>)

        fun showError(message: String?)
    }
    //TaskPresenterクラスでimplementされる想定のインターフェース
    interface Presenter{
        fun loadTasks()

        fun insertTask(description: String, importance: Float)

        fun updateTaskState(task: Task)

        fun updateTaskDescription(task: Task, description: String, importance: Float)

        fun deleteTask(task: Task)
    }
}