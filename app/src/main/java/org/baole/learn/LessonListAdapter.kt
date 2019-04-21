package org.baole.learn

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.baole.learn.data.Lessons

class LessonListAdapter(val fragment: LessonListFragment, val data: Lessons): RecyclerView.Adapter<LessonListItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonListItemHolder {
        return LessonListItemHolder(fragment, LayoutInflater.from(fragment.context).inflate(R.layout.lesson_list_item_holder, parent, false ))
    }

    override fun getItemCount() = data.lessons.size

    override fun onBindViewHolder(holder: LessonListItemHolder, position: Int) {
        holder.bind(data.lessons[position])
    }

}