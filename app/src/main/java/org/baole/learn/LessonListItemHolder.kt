package org.baole.learn

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.baole.learn.data.LessonInfo
import org.baole.learn.databinding.LessonListItemHolderBinding

class LessonListItemHolder(private val fragment: LessonListFragment, itemView: View) : RecyclerView.ViewHolder(itemView) {

    val binder = LessonListItemHolderBinding.bind(itemView)

    init {
        binder.root.setOnClickListener {
            fragment.onSelectLesson(it.tag as LessonInfo)
        }
    }


    fun bind(data: LessonInfo) {
        binder.title.text = data.name
        binder.root.tag = data

    }

}