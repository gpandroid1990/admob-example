package com.tibbytang.admob.example

import kotlin.math.abs

/**
 * 作者:tibbytang
 * 微信:tibbytang19900607
 * 创建于:2021-04-26 10:17
 */
object FastClickUtil {
    private const val TIME: Int = 500
    private var currentTime: Long = 0
    private var count = 0
    fun isNotFastClick(): Boolean {
        if (abs((System.currentTimeMillis() - currentTime)) > TIME) {
            count = 0
            currentTime = System.currentTimeMillis()
            return true
        }
        if (count == 0) {
            currentTime = System.currentTimeMillis()
            count++
        }
        return false
    }

    fun isNotFastClick(time: Int = 500): Boolean {
        if (abs((System.currentTimeMillis() - currentTime)) > time) {
            currentTime = System.currentTimeMillis()
            return true
        }
        currentTime = System.currentTimeMillis()
        return false
    }
}