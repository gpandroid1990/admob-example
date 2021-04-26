package com.tibbytang.admob.example.ads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.tibbytang.admob.example.BuildConfig
import com.tibbytang.admob.example.R
import com.tibbytang.admob.example.ScopedAppActivity

/**
 * 作者:tibbytang
 * 微信:tibbytang19900607
 * 创建于:2021-04-26 10:17
 * 激励广告
 */
class RewardedActivity : ScopedAppActivity(), OnUserEarnedRewardListener {
    private var mRewardedAd: RewardedAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewarded)
        loadAd()
    }

    private fun loadAd() {
        var adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            this,
            BuildConfig.ADMOB_REWARD_ADS,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    mRewardedAd = rewardedAd
                    // 弹出插屏广告的是时候，首先要判断当前界面是否已经不可见 ，比如进入一个页面，
                    // 然后加载完成后弹出插屏广告，在加载的过程中有可能应用退到后台，这时在弹出插屏广告，会导致
                    // 违反admob政策，禁止应用在后台弹广告
                    if (!mIsStop) {
                        if (mRewardedAd != null) {
                            mRewardedAd?.show(this@RewardedActivity, this@RewardedActivity)
                        } else {
                        }
                    }
                }
            })
    }

    override fun onUserEarnedReward(p0: RewardItem) {
        var rewardAmount = p0.amount
        var rewardType = p0.type
        // 用户获得奖励
    }
}