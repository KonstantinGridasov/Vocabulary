package com.gkreduction.vocabulary.presentation.ui.widjet

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.gkreduction.vocabulary.R

@SuppressLint("CustomViewStyleable")
class CustomToolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var image: ImageView? = null
    private var textToolBar: TextView? = null
    private var onTextClickListener = {}
    private var onImageClickListener = {}


    init {
        LayoutInflater.from(context).inflate(R.layout.toolbar_view, this)
        image = findViewById(R.id.toolbar_settings)
        textToolBar = findViewById(R.id.toolbar_text)

        image?.setOnClickListener { onImageClickListener.invoke() }
        textToolBar?.setOnClickListener { onTextClickListener.invoke() }
    }


    fun setVisibilityImageToolbar(destinationId: Int) {
        when (destinationId) {
            R.id.ExamFragment -> image?.visibility = VISIBLE
            else -> image?.visibility = GONE
        }
    }


    fun setListenerImage(function: () -> Unit) {
        this.onImageClickListener = function
    }

    fun setListenerText(function: () -> Unit) {
        this.onTextClickListener = function
    }

    fun setTextName(name: String) {
        textToolBar?.text = name
    }


}