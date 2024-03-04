package com.akishtp.dadboard

import android.inputmethodservice.InputMethodService
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.LinearLayout

class DadBoardIMEService:InputMethodService() {
    private val keys = arrayOf(
        arrayOf("okay","hello","welp"),
        arrayOf("k","👍","O.K"),
        arrayOf("kk","okk","hey"),
        arrayOf("","","          ",".",""),
    )
    override fun onCreateInputView(): View {
        val layout = layoutInflater.inflate(R.layout.keyboard_layout, null) as LinearLayout

        keys.forEach { row ->
            val rowLayout=LinearLayout(this)
            rowLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            rowLayout.orientation = LinearLayout.HORIZONTAL

            val totalWeight = row.map { it.length }.sum().toFloat()
            row.forEach { keyLabel ->
                val key = Button(this)
                key.text = keyLabel
                key.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                key.setOnClickListener {
                    val inputConnection = currentInputConnection
                    inputConnection?.commitText(keyLabel, 1)
                }
                key.setPadding(0,0,0,0)
                val keyWeight =  (2 + keyLabel.length.toFloat()) / totalWeight
                val keyLayoutParams = LinearLayout.LayoutParams(0, 130, keyWeight)
                keyLayoutParams.setMargins(-4, -4, -4, -4)
                key.layoutParams = keyLayoutParams

                rowLayout.addView(key)
            }
            layout.addView(rowLayout)
        }
        return layout
    }
}