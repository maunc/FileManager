package com.ax.filemanager.data

import androidx.annotation.DrawableRes
import java.io.Serializable

data class MainFileTypeData(
    val type: Int,
    val title: String,
    @DrawableRes val image: Int,
) : Serializable {
    companion object {
        const val MAIN_FILE_TYPE_IMAGE = 1
        const val MAIN_FILE_TYPE_VIDEO = 1
        const val MAIN_FILE_TYPE_AUDIO = 1
    }
}