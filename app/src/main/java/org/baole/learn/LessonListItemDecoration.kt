package org.baole.learn

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by baoleduc on 29/06/16.
 */
open class LessonListItemDecoration : RecyclerView.ItemDecoration {
    protected val mDivider: Drawable

    constructor(context: Context) : this(context, R.drawable.divider)

    constructor(context: Context, drawable: Int) {
        mDivider = context.resources.getDrawable(drawable)
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        var left = parent.paddingLeft
        var right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight

            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }

        drawVerticial(c, parent, state)


    }

    private fun drawVerticial(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom

        for (i in 0 until 3) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val left = child.right + params.rightMargin
            val right = left + mDivider.intrinsicWidth

            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }

}

