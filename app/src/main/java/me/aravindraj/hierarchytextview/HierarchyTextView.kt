package me.aravindraj.hierarchytextview


import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatTextView
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan
import android.util.AttributeSet


class HierarchyTextView : AppCompatTextView {

    private var mScale: Float = 0f
    private var entries: Array<out CharSequence>? = null
    private var spanDrawable: Drawable? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initPainters(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initPainters(attrs)
    }

    constructor(context: Context?) : super(context)


    private fun initPainters(attrs: AttributeSet) {
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.HierarchyTextView, 0, 0)
        mScale = resources.displayMetrics.density
        try {
            entries = attributes.getTextArray(R.styleable.HierarchyTextView_android_entries)
            spanDrawable = attributes.getDrawable(R.styleable.HierarchyTextView_spanDrawable)
            val ascent = paint.getFontMetrics().ascent
            val h = (-ascent).toInt()
            if (spanDrawable != null) {
                spanDrawable!!.setBounds(0, 0, h, h)
            }

            var charSequence: CharSequence = ""
            if (entries != null) {
                entries!!.forEachIndexed { index, it ->
                    val tempString = SpannableString("$it ")
                    if (index != entries!!.size - 1) {
                        tempString.setSpan(
                            ImageSpan(spanDrawable!!, DynamicDrawableSpan.ALIGN_BASELINE),
                            it.length,
                            it.length + 1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                    charSequence = TextUtils.concat(charSequence, tempString)
                }
            }
            this.text = charSequence

        } finally {
            attributes.recycle()
        }

    }

}