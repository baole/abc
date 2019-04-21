package org.baole.learn

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import org.baole.learn.data.LessonInfo
import org.baole.learn.data.Lessons
import org.baole.learn.data.LessonsViewModel
import org.baole.learn.databinding.LessonListFragmentBinding

class LessonListFragment : BaseFragment() {

    val TAG = LessonListFragment::class.java.simpleName

    var onSelectItem: ((LessonInfo) -> Unit)? = null

    private lateinit var binder: LessonListFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binder = LessonListFragmentBinding.inflate(inflater, container, false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()
    }


    fun loadData() {
        activity?.let {
            val viewModel = ViewModelProviders.of(it, LessonsViewModel.Factory(it.application)).get(LessonsViewModel::class.java)
            viewModel.liveData.observe(this, Observer<Lessons> { t ->
                t?.let {
                    onLessonsLoaded(it)
                } ?: Log.e(TAG, "Error while loading lessons")
            })

        }
    }

    private fun onLessonsLoaded(it: Lessons) {
        binder.recycler.layoutManager = GridLayoutManager(context, 3)
        binder.recycler.addItemDecoration(LessonListItemDecoration(context!!))
        binder.recycler.adapter = LessonListAdapter(this, it)
    }

    fun onSelectLesson(data: LessonInfo) {
        onSelectItem?.invoke(data)
    }
}