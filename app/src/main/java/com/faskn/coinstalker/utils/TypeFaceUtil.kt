package com.faskn.coinstalker.utils

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import java.lang.reflect.Field

class TypeFaceUtil {

    fun overrideFont(
        context: Context,
        defaultFontNameToOverride: String,
        customFontFileNameInAssets: String
    ) {


        try {
            val customFontTypeFace: Typeface =
                Typeface.createFromAsset(context.assets, customFontFileNameInAssets)
            val defaultFontTypefaceField: Field =
                Typeface::class.java.getDeclaredField(defaultFontNameToOverride)
            defaultFontTypefaceField.isAccessible = true
            defaultFontTypefaceField.set(null, customFontTypeFace)
        } catch (e: Error) {
            Log.e(
                "TypeFace Error",
                "Can not set custom font $customFontFileNameInAssets instead of $defaultFontNameToOverride"
            )
        }
    }
}