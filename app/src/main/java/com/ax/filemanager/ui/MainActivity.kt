package com.ax.filemanager.ui

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ax.base.ext.finishCurrentActivity
import com.ax.base.ext.gridLayoutManager
import com.ax.base.ext.linearLayoutManager
import com.ax.base.ext.postSmoothScrollToPosition
import com.ax.base.ext.toastShort
import com.ax.base.ui.BaseActivity
import com.ax.base.utils.checkFilePermission
import com.ax.base.utils.obtainSDCardRootPath
import com.ax.filemanager.adapter.FileAdapter
import com.ax.filemanager.adapter.FilePathGuideAdapter
import com.ax.filemanager.adapter.MainFileTypeAdapter
import com.ax.filemanager.data.FileData
import com.ax.filemanager.data.FilePathGuideData
import com.ax.filemanager.databinding.ActivityMainBinding
import com.ax.filemanager.viewmodel.MainViewModel
import java.io.File
import java.util.ArrayDeque

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private val fileStack = ArrayDeque<String>()

    private val fileAdapter by lazy {
        FileAdapter().apply {
            setOnItemClickListener { _, _, position ->
                val item = data.getOrNull(position) ?: return@setOnItemClickListener
                val currentPath = fileStack.last()
                val nextPath = File(currentPath, item.fileName).absolutePath
                if (item.fileType == FileData.LOCAL_FILE_TYPE_DIR) {
                    fileStack.addLast(nextPath)
                    mViewModel.loadDir(nextPath)
                } else {
                    toastShort(item.absolutePath)
                }
            }
        }
    }

    private val pathGuideAdapter by lazy {
        FilePathGuideAdapter().apply {
            setOnItemClickListener { _, _, position ->
                val item = data.getOrNull(position) ?: return@setOnItemClickListener
                val root = fileStack.firstOrNull() ?: return@setOnItemClickListener
                val crumbs = buildPathBreadcrumb(root, item.realPath)
                fileStack.clear()
                crumbs.forEach { fileStack.addLast(it.realPath) }
                mViewModel.loadDir(item.realPath)
            }
        }
    }

    private val mainFileTypeAdapter by lazy {
        MainFileTypeAdapter().apply {

        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        checkFilePermission()
        mDatabind.mainFileTypeRecycler.apply {
            layoutManager = gridLayoutManager(3)
            adapter = mainFileTypeAdapter
        }
        mDatabind.mainFilePathGuideRecycler.apply {
            layoutManager = linearLayoutManager(LinearLayoutManager.HORIZONTAL)
            adapter = pathGuideAdapter
        }
        mDatabind.mainFileRecycler.apply {
            layoutManager = linearLayoutManager()
            adapter = fileAdapter
        }
        mDatabind.mainSmartRefreshLayout.setOnRefreshListener {
            mViewModel.loadDir(fileStack.last())
        }
        val startPath = obtainSDCardRootPath()
        fileStack.addLast(startPath)
        mViewModel.loadDir(startPath)
        mainFileTypeAdapter.setList(mViewModel.mainFileTypeList)
    }

    override fun onBackPressCallBack() {
        loadLastPath()
    }

    override fun createObserver() {
        mViewModel.currentPathLiveData.observe(this) { path ->
            fileStack.firstOrNull()?.let { root ->
                val crumbs = buildPathBreadcrumb(root, path)
                pathGuideAdapter.setList(crumbs)
                if (crumbs.isNotEmpty()) {
                    mDatabind.mainFilePathGuideRecycler.postSmoothScrollToPosition(crumbs.size - 1)
                }
            }
        }
        mViewModel.fileListLiveData.observe(this) {
            fileAdapter.setList(it)
        }
        mViewModel.errorLiveData.observe(this) { msg ->
            toastShort(msg)
        }
    }

    private fun loadLastPath() {
        if (fileStack.size <= 1) {
            finishCurrentActivity()
        } else {
            fileStack.removeLast()
            val newPath = fileStack.last()
            mViewModel.loadDir(newPath)
        }
    }

    /**
     * 从根目录到当前路径生成面包屑
     */
    private fun buildPathBreadcrumb(
        rootPath: String,
        currentPath: String,
    ): MutableList<FilePathGuideData> {
        val rTrimEnd = rootPath.trimEnd('/')
        val cTrimEnd = currentPath.trimEnd('/')
        val pathGuideList = mutableListOf<FilePathGuideData>()
        if (!cTrimEnd.startsWith(rTrimEnd)) {
            pathGuideList.add(FilePathGuideData(cTrimEnd, File(cTrimEnd).name))
            return pathGuideList
        }
        pathGuideList.add(FilePathGuideData(rTrimEnd, "根目录"))
        val remainder = cTrimEnd.removePrefix(rTrimEnd).trimStart('/')
        if (remainder.isEmpty()) return pathGuideList
        var acc = rTrimEnd
        for (part in remainder.split('/').filter { it.isNotEmpty() }) {
            acc = if (acc.endsWith("/")) "$acc$part" else "$acc/$part"
            pathGuideList.add(FilePathGuideData(acc, part))
        }
        return pathGuideList
    }
}