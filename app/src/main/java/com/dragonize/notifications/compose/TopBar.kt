package com.dragonize.notifications.compose

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import android.widget.*
import androidx.core.content.ContextCompat
import com.dragonize.notifications.R
import com.dragonize.notifications.databinding.DialogAlertBinding

class TopBar(context: Context):
    RelativeLayout(context) {
    val LEFT_ID = -1
    val CENTER_ID = -2
    val RIGHT_ID = -3
    val left = LinearLayout(context)
    val center = LinearLayout(context)
    val right = LinearLayout(context)


    private val lpParent = LayoutParams(
        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
    )
    private val lpLeft = LayoutParams(
        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
    )
    private val lpRight = LayoutParams(
        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
    )


    init {
        left.id = LEFT_ID
        center.id = CENTER_ID
        right.id = RIGHT_ID

        this.background = ContextCompat.getDrawable(context, R.color.app)
        lpParent.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        this.layoutParams = lpParent

        left.background = ContextCompat.getDrawable(context, R.color.black)
        lpLeft.addRule(RelativeLayout.CENTER_VERTICAL)
        lpLeft.addRule(RelativeLayout.ALIGN_PARENT_START)
        left.layoutParams = lpLeft
        this.addView(left)

        right.background = ContextCompat.getDrawable(context, R.color.teal_700)
        lpRight.addRule(RelativeLayout.CENTER_VERTICAL)
        lpRight.addRule(RelativeLayout.ALIGN_PARENT_END)
        right.layoutParams = lpRight
        this.addView(right)


//        this.clipBounds.width() - left.clipBounds.width()*2
        left.viewTreeObserver
            .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    // TODO Auto-generated method stub
                    val w: Int = right.getWidth()
                    val h: Int = right.getHeight()

                    Log.v("W-H", "$w-$h")
                    this@TopBar.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            var lpCenter: LayoutParams
                            if ((width - w*2) > 0) {
                                lpCenter = LayoutParams(
                                    width - w*2, LayoutParams.WRAP_CONTENT
                                )
                                Log.v("Width :", "$w")
                            } else {
                                lpCenter = LayoutParams(
                                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
                                )
                                Toast.makeText(context, "Text can't be shown, not enough width!", Toast.LENGTH_LONG).show()
                            }

                            center.background = ContextCompat.getDrawable(context, R.color.purple_200)
                            lpCenter.addRule(RelativeLayout.CENTER_IN_PARENT)
                            lpCenter.addRule(RelativeLayout.END_OF, left.id)
                            lpCenter.addRule(RelativeLayout.START_OF, right.id)
                            center.layoutParams = lpCenter
                            this@TopBar.addView(center)
                            this@TopBar.viewTreeObserver
                                .removeOnGlobalLayoutListener(this)
                        }

                    })

                    left.viewTreeObserver
                        .removeOnGlobalLayoutListener(this)
                }
            })
    }


    // Defining the RelativeLayout layout parameters.
    // In this case I want to fill its parent

//    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
//    RelativeLayout.LayoutParams.FILL_PARENT,
//    RelativeLayout.LayoutParams.FILL_PARENT);


    // Defining the layout parameters of the TextView
//    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//    RelativeLayout.LayoutParams.WRAP_CONTENT,
//    RelativeLayout.LayoutParams.WRAP_CONTENT);
//    lp.addRule(RelativeLayout.CENTER_IN_PARENT);

    // Setting the parameters on the TextView
//    tv.setLayoutParams(lp);

    // Adding the TextView to the RelativeLayout as a child
//    relativeLayout.addView(tv);

    var onItemClick: ((click: String) -> Unit)? = null
    private var inflater: LayoutInflater = (context as Activity).layoutInflater
    private var binding: DialogAlertBinding = DialogAlertBinding.inflate(inflater)

    init {
        binding.root.setOnClickListener {  }
        binding.btnOk.setOnClickListener { (context as Activity).finish() }
    }

}

fun LinearLayout.addImageButton(id: Int, res: Int): ImageButton? {
    var btn = ImageButton(context)
    btn.id = id
    btn.setImageResource(res)
    this.addView(btn)
    return btn
}

fun LinearLayout.addTextView(id: Int, text: String): TextView? {
    var tv = TextView(context)
    tv.id = id
    tv.text = text
    this.addView(tv)
    return tv
}

fun LinearLayout.removeImageButton(id: Int) {
    val ib = this.findViewById<ImageButton>(id)
    this.removeView(ib)
}