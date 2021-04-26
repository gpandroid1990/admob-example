package com.tibbytang.admob.example

import android.util.DisplayMetrics
import android.view.Display
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * 作者:tibbytang
 * 微信:tibbytang19900607
 * 创建于:2021-04-26 10:17
 */
open class ScopedAppActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    protected var mIsStop = false
    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mIsStop = false
    }

    override fun onPause() {
        super.onPause()
        mIsStop = true
    }

    protected fun getAdSize(): AdSize? {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.

        val widthPixels = resources.displayMetrics.widthPixels.toFloat()
        val density = resources.displayMetrics.density
        val adWidth = (widthPixels / density).toInt()

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
    }
}