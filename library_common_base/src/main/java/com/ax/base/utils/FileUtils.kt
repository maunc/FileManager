package com.ax.base.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Environment
import java.io.File
import java.io.IOException

private const val KB = 1024L
private const val MB = KB * 1024
private const val GB = MB * 1024

/**
 * 格式化字符串
 */
@SuppressLint("DefaultLocale")
fun Long.formatFileSize(): String {
    return when {
        this >= GB -> String.format("%.2fGB", this.toFloat() / GB)
        this >= MB -> String.format("%.2fMB", this.toFloat() / MB)
        this >= KB -> String.format("%.2fKB", this.toFloat() / KB)
        this > 0 -> "${this}B"
        else -> "0 B"
    }
}

/**
 * 获取文件的字节大小（本地文件）
 * @return 字节数，文件不存在/不是文件返回0
 */
fun File.obtainFileSize() = if (this.exists() && this.isFile) this.length() else 0L

/**
 * 在SDCard根目录创建文件夹
 */
fun Context.createFileDirFromSdCard(dirName: String): Boolean {
    if (!isSDCardAvailable()) return false
    if (dirName.trim().isEmpty()) return false
    val sdCardRoot = File(obtainSDCardRootPath())
    val targetFile = File(sdCardRoot, dirName)
    try {
        if (targetFile.exists() && targetFile.isDirectory) return false
        return targetFile.mkdirs()
    } catch (e: Exception) {
        return false
    }
}

/**
 * 在某个文件下创建文件夹 String为rootPath
 */
fun File.createFileDir(dirName: String): Boolean {
    if (!this.exists()) return false
    if (dirName.trim().isEmpty()) return false
    val targetFile = File(this.absolutePath, dirName)
    try {
        if (targetFile.exists() && targetFile.isDirectory) return false
        return targetFile.mkdirs()
    } catch (e: Exception) {
        return false
    }
}

/**
 * 获取SDCARD跟目录
 */
@SuppressLint("ObsoleteSdkInt")
fun Context.obtainSDCardRootPath(): String {
    if (!isSDCardAvailable()) return ""
    val sdCardRoot: File? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        getExternalFilesDir(null)
    } else {
        Environment.getExternalStorageDirectory()
    }
    return if (sdCardRoot != null && sdCardRoot.isPathWritable()) sdCardRoot.absolutePath else ""
}

/**
 * 检查SD卡是否挂载且可用
 */
fun isSDCardAvailable(): Boolean {
    return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
}

/**
 * 检查路径是否可读写
 */
fun File.isPathWritable(): Boolean {
    if (!this.exists()) return false
    if (this.isFile) return false
    val testFile = File(this.absolutePath, "test_write.txt")
    try {
        if (testFile.createNewFile()) {
            testFile.delete()
            return true
        }
    } catch (e: IOException) {
        return false
    }
    return false
}