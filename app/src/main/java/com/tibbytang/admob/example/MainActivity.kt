package com.tibbytang.admob.example

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.elvishew.xlog.XLog
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.tibbytang.admob.example.ads.BannerActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 作者:tibbytang
 * 微信:tibbytang19900607
 * 创建于:2021-04-26 10:17
 */
class MainActivity : ScopedAppActivity(), View.OnClickListener {
    private var mInterstitialAd: InterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAds()
        banner_view.setOnClickListener(this)
        interstitial_view.setOnClickListener(this)
    }

    private fun initAds() {
        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            BuildConfig.ADMOB_INTERSTITIAL_ADS,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    XLog.d(adError?.message)
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    XLog.d("Ad was loaded.")
                    mInterstitialAd = interstitialAd

                }
            })
    }

    override fun onClick(v: View?) {
        if (v == banner_view) {
            startActivity(Intent(this, BannerActivity::class.java))
        }
        if (v == interstitial_view) {
            showInterstitialAds()
        }
    }

    private fun showInterstitialAds() {
        mInterstitialAd?.let {
            if (!mIsStop) {
                it.show(this@MainActivity)
                it.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        XLog.d("Ad was dismissed.")
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                        XLog.d("Ad failed to show.")
                    }

                    override fun onAdShowedFullScreenContent() {
                        XLog.d("Ad showed fullscreen content.")
                        mInterstitialAd = null;
                    }
                }
            }
        }
    }
}