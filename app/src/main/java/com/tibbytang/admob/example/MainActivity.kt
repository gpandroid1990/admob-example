package com.tibbytang.admob.example

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.elvishew.xlog.XLog
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.tibbytang.admob.example.ads.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 作者:tibbytang
 * 微信:tibbytang19900607
 * 创建于:2021-04-26 10:17
 */
class MainActivity : ScopedAppActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        banner_view.setOnClickListener(this)
        interstitial_view.setOnClickListener(this)
        interstitial_reward_view.setOnClickListener(this)
        reward_view.setOnClickListener(this)
        native_view.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        if (v == banner_view) {
            startActivity(Intent(this, BannerActivity::class.java))
        }
        if (v == interstitial_view) {
            startActivity(Intent(this, InterstitialActivity::class.java))
        }
        if (v == interstitial_reward_view) {
            startActivity(Intent(this, RewardedInterstitialAdActivity::class.java))
        }
        if (v == reward_view) {
            startActivity(Intent(this, RewardedActivity::class.java))
        }
        if (v == native_view) {
            startActivity(Intent(this, NativeActivity::class.java))
        }
    }
}