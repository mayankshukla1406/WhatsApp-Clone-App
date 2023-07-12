package com.example.whatsapp.util

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

class OutlineProvider : ViewOutlineProvider() {
    override fun getOutline(view : View, outline : Outline) {
        outline.setOval(0, 0, view.width, view.height)
    }
}