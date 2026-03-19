package com.ax.base

import android.app.Application
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import me.jessyan.autosize.AutoSizeConfig
import kotlin.coroutines.cancellation.CancellationException

open class BaseApp : Application() {

    companion object {
        lateinit var app: Application
        private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            if (throwable is CancellationException) {
                // 协程取消异常，无需处理（正常流程）
                return@CoroutineExceptionHandler
            }
        }
        val mGlobalScope = CoroutineScope(SupervisorJob() + Dispatchers.IO + exceptionHandler)
    }

    override fun onCreate() {
        super.onCreate()
        app = this

//        3200×1440 这是 px 分辨率。
//        按 AutoSize 的用法，
//        需要配置的是 设计稿的 dp 基准宽/高；通常 1440px 这种稿子对应的是 x4 标注（(1440/4=360dp)，(3200/4=800dp)）。
//        我把配置补全为 宽 360dp / 高 800dp，仍以宽为基准适配
        AutoSizeConfig.getInstance()
            .setBaseOnWidth(true)
            .setDesignWidthInDp(360)
            .setDesignHeightInDp(800)
            .setExcludeFontScale(true)
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}