package com.tibbytang.admob.example.ads

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.tibbytang.admob.example.BuildConfig
import com.tibbytang.admob.example.R
import com.tibbytang.admob.example.ScopedAppActivity
import kotlinx.android.synthetic.main.activity_native.*

/**
 * 作者:tibbytang
 * 微信:tibbytang19900607
 * 创建于:2021-04-26 10:17
 * 原生广告
 */
class NativeActivity : ScopedAppActivity() {
    private var mNativeAd: NativeAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native)
        loadNativeAds()
    }

    private fun loadNativeAds() {
        val adLoader = AdLoader.Builder(this, BuildConfig.ADMOB_NATIV_ADS)
            .forNativeAd { ad: NativeAd ->
                mNativeAd = ad
                // Show the ad.
                populateNativeAdView(native_ad_container_view, ad)
                // Assumes you have a placeholder FrameLayout in your View layout
                // (with id ad_frame) where the ad is to be placed.
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Handle the failure by logging, altering the UI, and so on.
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    // Methods in the NativeAdOptions.Builder class can be
                    // used here to specify individual options settings.
                    .build()
            )
            .build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    fun populateNativeAdView(parent: ViewGroup, ad: NativeAd) {

        // Inflate a layout and add it to the parent ViewGroup.
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater
        val adView = inflater.inflate(R.layout.native_ad_layout, null) as NativeAdView

        // Locate the view that will hold the headline, set its text, and use the
        // NativeAdView's headlineView property to register it.
        val headlineView = adView.findViewById<TextView>(R.id.ad_headline)
        headlineView.text = ad.headline
        adView.headlineView = headlineView

        // Repeat the above process for the other assets in the NativeAd using
        // additional view objects (Buttons, ImageViews, etc).

        val mediaView = adView.findViewById<MediaView>(R.id.ad_media)
        adView.mediaView = mediaView

        // Call the NativeAdView's setNativeAd method to register the
        // NativeAdObject.
        adView.setNativeAd(ad)

        // Ensure that the parent view doesn't already contain an ad view.
        parent.removeAllViews()

        // Place the AdView into the parent.
        parent.addView(adView)
    }

    override fun onDestroy() {
        mNativeAd?.let {
            it.destroy()
        }
        super.onDestroy()
    }
}