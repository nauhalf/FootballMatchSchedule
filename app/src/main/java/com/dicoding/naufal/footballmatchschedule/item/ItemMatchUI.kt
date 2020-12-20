package com.dicoding.naufal.footballmatchschedule.item

import android.graphics.Typeface
import androidx.core.content.ContextCompat
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.dicoding.naufal.footballmatchschedule.R
import com.dicoding.naufal.footballmatchschedule.R.color.colorPrimary
import com.dicoding.naufal.footballmatchschedule.R.dimen.horizontal_padding
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class ItemMatchUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        cardView {
            id = R.id.card_item
            radius = 5f
            elevation = 4f
            lparams(width = matchParent, height = wrapContent) {
                topMargin = resources.getDimensionPixelSize(R.dimen.common_padding)
                leftMargin = resources.getDimensionPixelSize(horizontal_padding)
                rightMargin = resources.getDimensionPixelSize(horizontal_padding)
                bottomPadding = resources.getDimensionPixelSize(R.dimen.common_padding)
                topPadding = resources.getDimensionPixelSize(R.dimen.common_padding)
            }
            isClickable = true
            foreground = with(TypedValue()) {
                context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
                ContextCompat.getDrawable(context, resourceId)
            }

            linearLayout {
                orientation = LinearLayout.VERTICAL
                lparams(width = matchParent, height = wrapContent) {
                    bottomPadding = resources.getDimensionPixelSize(R.dimen.common_padding)
                    topPadding = resources.getDimensionPixelSize(R.dimen.common_padding)
                    leftPadding = dip(15)
                    rightPadding = dip(15)
                }


                textView {
                    id = R.id.txt_schedule_date
                    gravity = Gravity.CENTER_HORIZONTAL
                    textColor = ContextCompat.getColor(context, colorPrimary)
                    textSize = 18f
                }.lparams(width = matchParent, height = wrapContent)

                textView {
                    id = R.id.txt_schedule_time
                    gravity = Gravity.CENTER_HORIZONTAL
                    textColor = ContextCompat.getColor(context, colorPrimary)
                    textSize = 15f
                }.lparams(width = matchParent, height = wrapContent)

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    bottomPadding = dip(7)
                    topPadding = dip(7)
                    weightSum = 11f

                    textView {
                        id = R.id.txt_home_team
                        ellipsize = TextUtils.TruncateAt.END
                        gravity = Gravity.RIGHT
                        maxLines = 1
                        textSize = 19f
                    }.lparams(width = dip(0), height = wrapContent) {
                        weight = 4f
                    }

                    textView {
                        id = R.id.txt_home_score
                        gravity = Gravity.RIGHT
                        textSize = 20f
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams(width = dip(0), height = wrapContent) {
                        weight = 1f
                    }

                    textView {
                        gravity = Gravity.CENTER_HORIZONTAL
                        textSize = 18f
                        text = resources.getString(R.string.vs)
                    }.lparams(width = dip(0), height = wrapContent) {
                        weight = 1f
                    }

                    textView {
                        id = R.id.txt_away_score
                        gravity = Gravity.LEFT
                        textSize = 20f
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams(width = dip(0), height = wrapContent) {
                        weight = 1f
                    }

                    textView {
                        id = R.id.txt_away_team
                        ellipsize = TextUtils.TruncateAt.END
                        gravity = Gravity.LEFT
                        maxLines = 1
                        textSize = 19f
                    }.lparams(width = dip(0), height = wrapContent) {
                        weight = 4f
                    }
                }.lparams(width = matchParent, height = matchParent)
            }
        }
    }
}