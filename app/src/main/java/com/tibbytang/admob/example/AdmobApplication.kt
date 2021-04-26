package com.tibbytang.admob.example

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.elvishew.xlog.BuildConfig
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.elvishew.xlog.printer.AndroidPrinter
import com.google.android.gms.ads.MobileAds

/**
 * 作者:tibbytang
 * 微信:tibbytang19900607
 * 创建于:2021-04-26 10:17
 */
class AdmobApplication : Application(), Application.ActivityLifecycleCallbacks {
    private var mForegroundActivityCount = 0
    private var lastTime: Long = 0
    private var isInbackground = false
    private val TIME = 1

    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initLog()
        initAds()
        registerActivityLifecycleCallbacks(this)
    }

    /**
     * 初始化广告SDK
     */
    private fun initAds() {
        MobileAds.initialize(this)
    }

    /**
     * 初始化log
     */
    private fun initLog() {
        val logConfiguration = LogConfiguration.Builder()
            .tag("admob-example")
            .logLevel(if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE)
            .build()
        XLog.init(logConfiguration, AndroidPrinter())
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
        // 当应用是从后台启动时，如果后天停留时间大于我们设定的时间，则启动开屏页面，增加广告展示
        if (isInbackground && lastTime != 0L && (System.currentTimeMillis() - lastTime > TIME * 60 * 1000)) {
            if (activity !is SplashActivity) {
                val intent = Intent(this, SplashActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("is_from_background", true)
                startActivity(intent)
            }
        }
        mForegroundActivityCount++
        if (mForegroundActivityCount == 1) {
            isInbackground = false
            XLog.d("应用进入前台")
        }
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
        mForegroundActivityCount--
        if (mForegroundActivityCount == 0) {
            lastTime = System.currentTimeMillis()
            isInbackground = true
            XLog.d("应用进入后台")
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}