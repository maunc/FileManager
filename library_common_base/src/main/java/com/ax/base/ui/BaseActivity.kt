package com.ax.base.ui

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.ax.base.ext.finishCurrentActivity
import com.ax.base.ext.getVmClazz
import com.ax.base.ext.inflateBindingWithGeneric

abstract class BaseActivity<VM : BaseViewModel<*>, DB : ViewDataBinding> : AppCompatActivity() {

    lateinit var mViewModel: VM

    lateinit var mDatabind: DB

    private val backPressCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressCallBack()
        }
    }

    /**
     * 返回键 默认是销毁当前页面，可以重写定制
     */
    open fun onBackPressCallBack() {
        finishCurrentActivity()
    }

    /**
     * 加载view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 创建LiveData数据观察者
     */
    abstract fun createObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mDatabind = inflateBindingWithGeneric(layoutInflater)
        mDatabind.lifecycleOwner = this
        setContentView(mDatabind.root)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        mViewModel = createViewModel()
        lifecycle.addObserver(mViewModel)
        initView(savedInstanceState)
        onBackPressedDispatcher.addCallback(this, backPressCallback)
        createObserver()
    }

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
//        return ViewModelProvider(this)[getVmClazz(this)]
        return defaultViewModelProviderFactory.create(getVmClazz(this))
    }

    /**
     * 获取ViewModel
     */
    fun <T : BaseViewModel<*>> getViewModel(quickViewModel: Class<T>): T {
        return ViewModelProvider(this)[quickViewModel]
    }
}