package org.baole.learn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import org.baole.learn.data.LessonDetailsViewModel
import org.baole.learn.data.LessonsViewModel
import org.baole.learn.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(){

     val TAG = MainActivity::class.java.simpleName

    private lateinit var binder: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)
        setSupportActionBar(binder.toolbar)
        loadData()

        supportFragmentManager.beginTransaction().add(R.id.container, LessonFragment()).commit()
    }

    fun loadData() {
        val viewModel = ViewModelProviders.of(this, LessonsViewModel.Factory(application)).get(LessonsViewModel::class.java)
        val detailViewModel = ViewModelProviders.of(this).get(LessonDetailsViewModel::class.java)

    }
}
