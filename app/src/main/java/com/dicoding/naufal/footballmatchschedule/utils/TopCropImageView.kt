package com.dicoding.naufal.footballmatchschedule.utils

import android.annotation.TargetApi
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView

class TopCropImageView : androidx.appcompat.widget.AppCompatImageView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        recomputeImgMatrix()
    }

    override fun setFrame(l: Int, t: Int, r: Int, b: Int): Boolean {
        recomputeImgMatrix()
        return super.setFrame(l, t, r, b)
    }

    private fun init() {
        scaleType = ImageView.ScaleType.MATRIX
    }

    private fun recomputeImgMatrix() {

        val matrix = imageMatrix

        val drawable: Drawable = drawable ?: return

        val scale: Float
        val viewWidth = width - paddingLeft - paddingRight
        val viewHeight = height - paddingTop - paddingBottom
        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight

        if (drawableWidth * viewHeight > drawableHeight * viewWidth) {
            scale = viewHeight.toFloat() / drawableHeight.toFloat()
        } else {
            scale = viewWidth.toFloat() / drawableWidth.toFloat()
        }

        matrix.setScale(scale, scale)
        if (drawableWidth * scale > viewWidth) {
            val tr = -((drawableWidth * scale - viewWidth) / 2)
            matrix.postTranslate(tr, 0.5f)
        }
        imageMatrix = matrix
    }
}
