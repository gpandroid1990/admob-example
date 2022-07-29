package com.tibbytang.admob.example.ads

import android.os.Bundle
import com.elvishew.xlog.XLog
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.tibbytang.admob.example.BuildConfig
import com.tibbytang.admob.example.R
import com.tibbytang.admob.example.ScopedAppActivity

/**
 * 作者:tibbytang
 * 微信:tibbytang19900607
 * 创建于:2021-04-26 10:17
 * 插页广告
 */
class InterstitialActivity : ScopedAppActivity() {
    private var mInterstitialAd: InterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interstitial)
        initAds()
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
                    showInterstitialAds()
                }
            })
    }

    /**
     * 显示插屏广告
     */
    private fun showInterstitialAds() {
        mInterstitialAd?.let {
            // 弹出插屏广告的是时候，首先要判断当前界面是否已经不可见 ，比如进入一个页面，
            // 然后加载完成后弹出插屏广告，在加载的过程中有可能应用退到后台，这时在弹出插屏广告，会导致
            // 违反admob政策，禁止应用在后台弹广告
            if (!mIsStop) {
                it.show(this@InterstitialActivity)
                it.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        XLog.d("Ad was dismissed.")
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        super.onAdFailedToShowFullScreenContent(p0)
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