package com.ax.filemanager.adapter

import com.ax.base.ext.loadImage
import com.ax.base.ext.loadImageRounded
import com.ax.base.ext.visibleOrGone
import com.ax.filemanager.R
import com.ax.filemanager.data.FileData
import com.ax.filemanager.databinding.ItemFileBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * FTP 远程文件列表适配器
 */
class FileAdapter :
    BaseQuickAdapter<FileData, BaseDataBindingHolder<ItemFileBinding>>(
        R.layout.item_file,
    ) {

    override fun convert(
        holder: BaseDataBindingHolder<ItemFileBinding>,
        item: FileData,
    ) {
        holder.dataBinding?.apply {
            itemFileNameTv.text = item.fileName
            itemFileExpandIv.visibleOrGone(item.fileType == FileData.LOCAL_FILE_TYPE_DIR)
            when (item.fileType) {
                FileData.LOCAL_FILE_TYPE_DIR -> {
                    itemFileTypeImage.setImageResource(R.drawable.icon_file_type_dir)
                }

                FileData.LOCAL_FILE_TYPE_IMAGE -> {
                    itemFileTypeImage.loadImageRounded(item.absolutePath)
                }

                FileData.LOCAL_FILE_TYPE_VIDEO -> {
                    itemFileTypeImage.setImageResource(R.drawable.icon_file_type_video)
                }

                FileData.LOCAL_FILE_TYPE_AUDIO -> {
                    itemFileTypeImage.setImageResource(R.drawable.icon_file_type_audio)
                }

                FileData.LOCAL_FILE_TYPE_WORD -> {
                    itemFileTypeImage.setImageResource(R.drawable.icon_file_type_word)
                }

                FileData.LOCAL_FILE_TYPE_PDF -> {
                    itemFileTypeImage.setImageResource(R.drawable.icon_file_type_pdf)
                }

                FileData.LOCAL_FILE_TYPE_PPT -> {
                    itemFileTypeImage.setImageResource(R.drawable.icon_file_type_ppt)
                }

                FileData.LOCAL_FILE_TYPE_XLS -> {
                    itemFileTypeImage.setImageResource(R.drawable.icon_file_type_xls)
                }

                FileData.LOCAL_FILE_TYPE_ARCHIVE -> {
                    itemFileTypeImage.setImageResource(R.drawable.icon_file_type_zip)
                }

                FileData.LOCAL_FILE_TYPE_APK -> {
                }

                FileData.LOCAL_FILE_TYPE_TEXT -> {
                    itemFileTypeImage.setImageResource(R.drawable.icon_file_type_txt)
                }

                FileData.LOCAL_FILE_TYPE_UNKNOWN -> {
                    itemFileTypeImage.setImageResource(R.drawable.icon_file_type_unknown)
                }
            }
        }
    }
}

