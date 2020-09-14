package com.yuki.TODO

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(),TaskContract.View {

    override fun onLoadTasks(tasks: List<Task>) {
        adapter.tasks = tasks
    }

    private lateinit var adapter: TasksAdapter
    private lateinit var presenter: TaskPresenter

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = context?: return

        adapter = TasksAdapter(itemListener)
        listView.adapter = adapter

        val db = TaskDatabase.getInstance(context)
        presenter = TaskPresenter(TaskRepository(db.taskDao()), this)
        presenter.loadTasks()


        addTaskButton.setOnClickListener {
            val editText = EditText(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            }

            val ratingDescription = TextView(context).apply{
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = "重要度"
            }

            val ratingBar = RatingBar(context).apply{
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                numStars = 5
                stepSize = 1F
            }

            val container = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(48, 0, 48, 0)
                addView(editText)
                addView(ratingDescription)
                addView(ratingBar)
            }

            AlertDialog.Builder(context)
                .setTitle(getString(R.string.add_dialog_title))
                .setView(container)
                .setPositiveButton(getString(R.string.ok), {dialog, which ->
                    presenter.insertTask(editText.text.toString(),ratingBar.rating)
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .show()
        }

    }


  override fun showError(message: String?) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    val itemListener = object : TaskItemListener{
        override fun onStateClick(task: Task) {
            presenter.updateTaskState(task)
        }

        override fun onDescriptionClick(task: Task) {
            val context = context?: return

            val editText = EditText(context).apply{
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
                setText(task.description)
            }

            val ratingDescription = TextView(context).apply{
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = "重要度"
            }

            val ratingBar = RatingBar(context).apply{
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                rating = task.importance ?: 0F
                numStars = 5
                stepSize = 1F
            }

            val container = LinearLayout(context).apply{
                orientation = LinearLayout.VERTICAL
                setPadding(48,0,48,0)
                addView(editText)
                addView(ratingDescription)
                addView(ratingBar)
            }

            AlertDialog.Builder(context)
                .setTitle(getString(R.string.edit_dialog_title))
                .setView(container)
                .setPositiveButton(getString(R.string.ok),{dialog,which->
                    presenter.updateTaskDescription(task,editText.text.toString(),ratingBar.rating)
                })
                .setNegativeButton(getString(R.string.cancel),null)
                .show()
        }

        override fun onDeleteClick(task: Task) {
            presenter.deleteTask(task)
        }
    }


    private class TasksAdapter(private val listener: TaskItemListener): BaseAdapter() {
        private val stateTexts = listOf(R.string.todo,R.string.doing,R.string.done)
        private val stateColors = listOf(R.color.todo,R.color.doing,R.color.done)
        var tasks: List<Task> = listOf()
            set(tasks){
                field = tasks
                notifyDataSetChanged()
            }

        override fun getCount() = tasks.size
        override fun getItem(i: Int) = tasks[i]
        override fun getItemId(i: Int) = i.toLong()
        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            val task = getItem(i)
            val rowView = view ?: LayoutInflater.from(viewGroup.context).inflate(R.layout.task_item,viewGroup, false)

            rowView.findViewById<TextView>(R.id.taskState).apply{
                text = context.getString(stateTexts[task.state])
                setTextColor(ContextCompat.getColor(context, stateColors[task.state]))
                setOnClickListener{
                    listener.onStateClick(task)
                }
            }

            rowView.findViewById<TextView>(R.id.taskDescription).apply{
                text = task.description
                setOnClickListener{
                    listener.onDescriptionClick(task)
                }
            }
            rowView.findViewById<ImageView>(R.id.taskDeleteButton).setOnClickListener{
                listener.onDeleteClick(task)
            }
            return rowView
        }
    }

    interface TaskItemListener {
        fun onStateClick(task: Task)
        fun onDescriptionClick(task: Task)
        fun onDeleteClick(task: Task)
    }
}