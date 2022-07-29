package com.tibbytang.admob.example.ads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.size
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.tibbytang.admob.example.BuildConfig
import com.tibbytang.admob.example.R
import kotlinx.android.synthetic.main.activity_admob.*

/**
 * 作者:tibbytang
 * 微信:tibbytang19900607
 * 创建于:2021-04-26 10:17
 * banner 广告
 */
class BannerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admob)

        val adView = AdView(this)
        // 设置广告大小
        adView.setAdSize(AdSize.BANNER)
        // 设置id
        adView.adUnitId = BuildConfig.ADMOB_BANNER_ADS
        adView.loadAd(AdRequest.Builder().build())
        banner_ad_container_view.addView(adView)
    }
}