package com.dicoding.naufal.footballmatchschedule.item

import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import com.dicoding.naufal.footballmatchschedule.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout.constraintLayout

class ItemTeamUI : AnkoComponent<ViewGroup> {
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
                        id = R.id.img_team_logo
                    }.lparams(width = dip(50), height = dip(50)) {
                        topToTop = ConstraintSet.PARENT_ID
                        bottomToBottom = ConstraintSet.PARENT_ID
                        leftToLeft = ConstraintSet.PARENT_ID
                    }

                    textView {
                        id = R.id.txt_team_name
                        textSize = 16f
                    }.lparams(width = dip(0), height = wrapContent) {
                        topToTop = ConstraintSet.PARENT_ID
                        bottomToBottom = ConstraintSet.PARENT_ID
                        leftToRight = R.id.img_team_logo
                        rightToLeft = R.id.img_view_detail
                        leftMargin = resources.getDimensionPixelSize(R.dimen.common_padding)
                    }

                    imageView {
                        id = R.id.img_view_detail
                        setImageResource(R.drawable.ic_chevron_right)

                    }.lparams(width = wrapContent, height = wrapContent) {
                        topToTop = ConstraintSet.PARENT_ID
                        bottomToBottom = ConstraintSet.PARENT_ID
                        rightToRight = ConstraintSet.PARENT_ID
                    }
                }
            }
        }
    }
}