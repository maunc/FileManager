package com.ax.filemanager.adapter

import com.ax.base.ext.obtainColor
import com.ax.filemanager.R
import com.ax.filemanager.data.MainFileTypeData
import com.ax.filemanager.databinding.ItemMainFileTypeBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

class MainFileTypeAdapter :
    BaseQuickAdapter<MainFileTypeData, BaseDataBindingHolder<ItemMainFileTypeBinding>>(
        R.layout.item_main_file_type,
    ) {

    override fun convert(
        holder: BaseDataBindingHolder<ItemMainFileTypeBinding>,
        item: MainFileTypeData,
    ) {
        holder.dataBinding?.apply {
            itemMainFileTypeIv.setImageResource(item.image)
            itemMainFileTypeTv.text = item.title
            itemMainFileTypeTv.setTextColor(obtainColor(R.color.black))
        }
    }
}