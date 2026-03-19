package com.ax.filemanager.adapter

import com.ax.base.ext.obtainColor
import com.ax.base.ext.obtainDrawable
import com.ax.base.ext.visibleOrGone
import com.ax.filemanager.R
import com.ax.filemanager.data.FilePathGuideData
import com.ax.filemanager.databinding.ItemFilePathGuideBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

class FilePathGuideAdapter :
    BaseQuickAdapter<FilePathGuideData, BaseDataBindingHolder<ItemFilePathGuideBinding>>(
        R.layout.item_file_path_guide
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemFilePathGuideBinding>,
        item: FilePathGuideData,
    ) {
        holder.dataBinding?.apply {
            itemFilePathGuideTv.text = item.lastPathName
            val isLast = holder.bindingAdapterPosition == data.size - 1
            itemFilePathGuideArrow.visibleOrGone(!isLast)
            itemFilePathGuideTv.setTextColor(
                if (isLast) obtainColor(R.color.white) else obtainColor(R.color.black)
            )
            itemFilePathGuideTv.setBackgroundDrawable(
                if (isLast) obtainDrawable(R.drawable.bg_radius_24_blue)
                else obtainDrawable(R.drawable.bg_radius_24_f2f2f2)
            )
        }
    }
}