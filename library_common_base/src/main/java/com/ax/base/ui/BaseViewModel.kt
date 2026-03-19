package com.ax.base.ui

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.ParameterizedType

/**
 *  调用携程
 */
fun <T> BaseViewModel<*>.launch(
    block: () -> T,
    success: (T) -> Unit = {},
    error: (Throwable) -> Unit = {},
) = viewModelScope.launch {
    kotlin.runCatching {
        withContext(Dispatchers.IO) {
            block()
        }
    }.onSuccess {
        success(it)
    }.onFailure {
        error(it)
    }
}

abstract class BaseViewModel<M : BaseModel?> : ViewModel(), BaseLifecycle {

    var model: M? = null

    init {
        val parameterizedType = javaClass.genericSuperclass as ParameterizedType
        val actualTypeArguments = parameterizedType.actualTypeArguments
        val mClass = actualTypeArguments[0] as Class<M>
        try {
            model = mClass.newInstance()
        } catch (e: IllegalAccessException) {
            Log.e("BaseViewModel", e.toString())
        } catch (e: InstantiationException) {
            Log.e("BaseViewModel", e.toString())
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        model?.onCreate(owner)
    }

    override fun onStart(owner: LifecycleOwner) {
        model?.onStart(owner)
    }

    override fun onStop(owner: LifecycleOwner) {
        model?.onStop(owner)
    }

    override fun onResume(owner: LifecycleOwner) {
        model?.onResume(owner)
    }

    override fun onPause(owner: LifecycleOwner) {
        model?.onPause(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        model?.onDestroy(owner)
    }
}