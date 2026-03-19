package com.ax.filemanager.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ax.base.ext.mutableListInsert
import com.ax.base.ui.BaseModel
import com.ax.base.ui.BaseViewModel
import com.ax.base.ui.launch
import com.ax.filemanager.R
import com.ax.filemanager.data.FileData
import com.ax.filemanager.data.MainFileTypeData
import java.io.File

class MainViewModel : BaseViewModel<BaseModel>() {

    val currentPathLiveData = MutableLiveData<String>()
    val fileListLiveData = MutableLiveData<MutableList<FileData>>()
    val errorLiveData = MutableLiveData<String>()

    val mainFileTypeList = mutableListOf<MainFileTypeData>().mutableListInsert(
        MainFileTypeData(
            type = MainFileTypeData.MAIN_FILE_TYPE_IMAGE,
            title = "图片",
            image = R.drawable.icon_file_type_dir
        ),
        MainFileTypeData(
            type = MainFileTypeData.MAIN_FILE_TYPE_VIDEO,
            title = "视频",
            image = R.drawable.icon_file_type_dir
        ),
        MainFileTypeData(
            type = MainFileTypeData.MAIN_FILE_TYPE_AUDIO,
            title = "音频",
            image = R.drawable.icon_file_type_dir
        ),
    )

    /**
     * 列出指定路径下的文件/文件夹（目录优先）
     */
    fun loadDir(path: String) {
        launch({
            val dir = File(path)
            val list = dir.listFiles()?.toMutableList().orEmpty()

            val sorted = list.sortedWith(
                compareBy<File> { !it.isDirectory }.thenBy { it.name.lowercase() }
            )
            sorted.mapNotNull { file ->
                val name = file.name ?: return@mapNotNull null
                if (name.isBlank()) return@mapNotNull null
                val fileExtension = file.extension.takeIf { it.isNotBlank() }
                val fileType = if (file.isDirectory) {
                    FileData.LOCAL_FILE_TYPE_DIR
                } else {
                    FileData.getFileTypeFromExtension(fileExtension)
                }
                FileData(
                    fileType = fileType,
                    fileName = name,
                    absolutePath = file.absolutePath,
                    sizeBytes = if (file.isDirectory) 0L else file.length(),
                    lastModifiedMillis = file.lastModified(),
                    extension = fileExtension,
                    isHidden = file.isHidden,
                    canRead = file.canRead(),
                    canWrite = file.canWrite(),
                    canExecute = file.canExecute(),
                    parentPath = file.parentFile?.absolutePath,
                    exists = file.exists(),
                )
            }.toMutableList()
        }, { data ->
            currentPathLiveData.postValue(path)
            fileListLiveData.postValue(data)
        }, { t ->
            errorLiveData.postValue(t.message ?: "列目录失败")
        })
    }
}