package com.dicoding.naufal.footballmatchschedule.item

import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import com.dicoding.naufal.footballmatchschedule.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout.constraintLayout

class ItemPlayerUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        cardView {
            id = R.id.card_item
            radius = 5f
            elevation = 4f
            lparams(width = matchParent, height = wrapContent) {
                topMargin = resources.getDimensionPixelSize(R.dimen.common_padding)
                leftMargin = resources.getDimensionPixelSize(R.dimen.horizontal_padding)
                rightMargin = resources.getDimensionPixelSize(R.dimen.horizontal_padding)
                bottomPadding = resources.getDimensionPixelSize(R.dimen.common_padding)
                topPadding = resources.getDimensionPixelSize(R.dimen.common_padding)
            }
            isClickable = true
            foreground = with(TypedValue()) {
                context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
                ContextCompat.getDrawable(context, resourceId)
            }

            constraintLayout {
                lparams(width = matchParent, height = matchParent) {
                    bottomPadding = resources.getDimensionPixelSize(R.dimen.common_padding)
                    topPadding = resources.getDimensionPixelSize(R.dimen.common_padding)
                    leftPadding = dip(15)
                    rightPadding = dip(15)

                    imageView {
                        id = R.id.img_player
                    }.lparams(width = dip(50), height = dip(50)) {
                        topToTop = ConstraintSet.PARENT_ID
                        bottomToBottom = ConstraintSet.PARENT_ID
                        leftToLeft = ConstraintSet.PARENT_ID
                    }

                    textView {
                        id = R.id.txt_player_name
                        textSize = 16f
                    }.lparams(width = dip(0), height = wrapContent) {
                        topToTop = ConstraintSet.PARENT_ID
                        leftMargin = resources.getDimensionPixelSize(R.dimen.common_padding)
                        bottomToBottom = ConstraintSet.PARENT_ID
                        leftToRight = R.id.img_player
                        rightToLeft = R.id.txt_player_position
                    }

                    textView {
                        id = R.id.txt_player_position
                        textSize = 16f
                    }.lparams(width = wrapContent, height = wrapContent) {
                        rightToRight = PARENT_ID
                        topToTop = PARENT_ID
                        bottomToBottom = PARENT_ID
                    }
                }
            }
        }
    }
}