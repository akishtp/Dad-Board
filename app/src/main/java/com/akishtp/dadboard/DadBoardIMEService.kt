package com.akishtp.dadboard


import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.inputmethodservice.InputMethodService
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat

class DadBoardIMEService:InputMethodService() {
    private val keys = arrayOf(
        arrayOf("kkk","okke","okay","okie"),
        arrayOf("'kay","👍","O.K","☑️","kk"),
        arrayOf("k","🆗","ok","okAy"),
        arrayOf("✅","okk","Okay","👌","←"),
        arrayOf(".","          ","↵")
    )
    override fun onCreateInputView(): View {
        val layout = layoutInflater.inflate(R.layout.keyboard_layout, null) as LinearLayout

        keys.forEachIndexed { index, row ->
            val rowLayout=LinearLayout(this)
            if(index==0 || index == 3 || index == 4){
                rowLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            }else {
                rowLayout.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }
            rowLayout.orientation = LinearLayout.HORIZONTAL

            val totalWeight = row.sumOf { it.length }.toFloat()
            row.forEach { keyLabel ->
                val key = Button(this)
                key.text = keyLabel
                key.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                key.setOnClickListener {
                    val inputConnection = currentInputConnection
                    inputConnection?.commitText(keyLabel, 1)
                }
                key.setPadding(0,0,0,0)
                val color = ContextCompat.getColor(this, R.color.primary80)

                // Add rounded corners and border
                val cornerRadius = resources.displayMetrics.density * 16 // 8dp
                val borderWidth = resources.displayMetrics.density * 6 // 2dp
                val shapeDrawable = GradientDrawable()
                shapeDrawable.cornerRadius = cornerRadius
                shapeDrawable.setStroke(borderWidth.toInt(), Color.TRANSPARENT) // Black border with 2dp width
                shapeDrawable.setColor(color)
                key.background = shapeDrawable

                val keyWeight = (2 + keyLabel.length.toFloat()) / totalWeight
                val keyLayoutParams = LinearLayout.LayoutParams(0, 120, keyWeight)
                keyLayoutParams.setMargins(4, 4, 4, 4)
                key.layoutParams = keyLayoutParams

                rowLayout.addView(key)
            }
            layout.addView(rowLayout)
        }
        return layout
    }
}
