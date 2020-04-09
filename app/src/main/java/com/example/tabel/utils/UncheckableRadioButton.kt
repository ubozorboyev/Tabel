package com.example.tabel.utils

import android.content.Context
import android.util.AttributeSet
import android.widget.RadioGroup
import androidx.appcompat.widget.AppCompatRadioButton


class UncheckableRadioButton :AppCompatRadioButton{

    constructor(context: Context):super(context)
    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet)
    constructor(context: Context,attributeSet: AttributeSet,defStle:Int):super(context,attributeSet,defStle)


    override fun toggle() {

        if (isChecked) {
            if (parent != null && parent is RadioGroup) {
                (parent as RadioGroup).clearCheck()
            }
        } else {
            super.toggle()
        }
    }

    override fun getAccessibilityClassName(): CharSequence? {
        return UncheckableRadioButton::class.java.name
    }

}