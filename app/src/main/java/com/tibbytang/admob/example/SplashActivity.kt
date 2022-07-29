package com.tibbytang.admob.example

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.elvishew.xlog.XLog
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 作者:tibbytang
 * 微信:tibbytang19900607
 * 创建于:2021-04-26 10:17
 */
class SplashActivity : ScopedAppActivity() {
    private var loadCallback: AppOpenAd.AppOpenAdLoadCallback? = null
    private var mOpenAdsShow = false
    private var mIsFromBackground = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)
        mIsFromBackground = intent.getBooleanExtra("is_from_background", false)
        launch {
            delay(5000)
            if (!mOpenAdsShow) {
                gotoMain()
            }
        }
        fetchAd()
    }

    /** Request an ad  */
    private fun fetchAd() {
        // We will implement this below.
        loadCallback = object : AppOpenAd.AppOpenAdLoadCallback() {
            override fun onAdLoaded(p0: AppOpenAd) {
                super.onAdLoaded(p0)
                if (!mIsStop && null != p0) {
                    p0.show(this@SplashActivity)
                    p0.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                            super.onAdFailedToShowFullScreenContent(p0)
                            gotoMain()
                        }

                        override fun onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent()
                            mOpenAdsShow = true
                        }

                        override fun onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent()
                            gotoMain()
                        }

                        override fun onAdImpression() {
                            super.onAdImpression()
                        }
                    }
                }
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                XLog.d("开屏广告加载错误 ${p0.message}")
                gotoMain()
            }
        }
        val request: AdRequest = AdRequest.Builder().build()
        AppOpenAd.load(
            this, BuildConfig.ADMOB_OPEN_ADS, request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback as AppOpenAd.AppOpenAdLoadCallback
        )
    }

    private fun gotoMain() {
        if (FastClickUtil.isNotFastClick(2000)) {
            if (mIsFromBackground) {
                finish()
            } else {
                launch(Dispatchers.IO) {
                    delay(50)
                    withContext(Dispatchers.Main) {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}