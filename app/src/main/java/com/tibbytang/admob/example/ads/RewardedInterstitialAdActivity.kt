package com.tibbytang.admob.example.ads

import android.os.Bundle
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import com.tibbytang.admob.example.BuildConfig
import com.tibbytang.admob.example.R
import com.tibbytang.admob.example.ScopedAppActivity

/**
 * 作者:tibbytang
 * 微信:tibbytang19900607
 * 创建于:2021-04-26 10:17
 * 插页激励广告
 */
class RewardedInterstitialAdActivity : ScopedAppActivity(), OnUserEarnedRewardListener {
    private var rewardedInterstitialAd: RewardedInterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewarded_interstitial_ad)
        loadAd()
    }

    private fun loadAd() {
        RewardedInterstitialAd.load(
            this,
            BuildConfig.ADMOB_INTERSTITIAL_REWARD_ADS,
            AdRequest.Builder().build(),
            object :
                RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(p0: RewardedInterstitialAd) {
                    super.onAdLoaded(p0)
                    rewardedInterstitialAd = p0
                    // 弹出插屏广告的是时候，首先要判断当前界面是否已经不可见 ，比如进入一个页面，
                    // 然后加载完成后弹出插屏广告，在加载的过程中有可能应用退到后台，这时在弹出插屏广告，会导致
                    // 违反admob政策，禁止应用在后台弹广告
                    if (!mIsStop) {
                        showAd()
                    }
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    rewardedInterstitialAd = null
                }
            })
    }

    private fun showAd() {
        rewardedInterstitialAd?.let {
            it.show(this@RewardedInterstitialAdActivity, this)
        }
    }

    override fun onUserEarnedReward(p0: RewardItem) {
        // 观看完毕，获得奖赏
    }
}