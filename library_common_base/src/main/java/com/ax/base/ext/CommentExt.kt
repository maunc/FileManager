package com.ax.base.ext

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ax.base.BaseApp
import com.ax.base.R
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

fun developmentToast() = toastShort(
    obtainString(R.string.functions_are_under_development)
)

fun toast(text: String, time: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(BaseApp.app, text, time).show()

fun toastLong(
    text: String,
) = Toast.makeText(BaseApp.app, text, Toast.LENGTH_LONG).show()

fun toastShort(
    text: String,
) = Toast.makeText(BaseApp.app, text, Toast.LENGTH_SHORT).show()

fun obtainString(
    @StringRes strRes: Int,
): String = ContextCompat.getString(BaseApp.app, strRes)

fun obtainDimens(
    @DimenRes dimenRes: Int,
): Int = BaseApp.app.resources.getDimensionPixelOffset(dimenRes)

fun obtainDimensFloat(
    @DimenRes dimenRes: Int,
): Float = BaseApp.app.resources.getDimension(dimenRes)

fun obtainDrawable(
    @DrawableRes drawableRes: Int,
): Drawable? = ContextCompat.getDrawable(BaseApp.app, drawableRes)

fun obtainColor(
    @ColorRes colorRes: Int,
): Int = ContextCompat.getColor(BaseApp.app, colorRes)

fun obtainColorToARAG(
    a: Int = 255, r: Int = 0, g: Int = 0, b: Int = 0,
) = Color.argb(a, r, g, b)

fun obtainColorStateList(
    @ColorRes colorRes: Int,
) = ContextCompat.getColorStateList(BaseApp.app, colorRes)

//获取屏幕宽度
fun obtainScreenWidth() = BaseApp.app.resources.displayMetrics.widthPixels

//获取屏幕高度
fun obtainScreenHeight() = BaseApp.app.resources.displayMetrics.heightPixels

/**
 * 获取屏幕高度
 * 可以选择是否带状态栏
 */
@SuppressLint("InternalInsetResource", "DiscouragedApi")
fun Context.obtainScreenHeight(isAddStatusBar: Boolean): Int {
    val heightPixels = resources.displayMetrics.heightPixels
    if (isAddStatusBar) {
        var statusBarHeight = 0
        val resourceId =
            resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        return heightPixels + statusBarHeight
    }
    return heightPixels
}

fun Int.dp2px(): Int =
    (this * BaseApp.app.resources.displayMetrics.density + 0.5f).toInt()

fun Int.px2dp(): Int =
    (this / BaseApp.app.resources.displayMetrics.density + 0.5f).toInt()

@SuppressLint("WrongConstant")
fun Context.linearLayoutManager(
    orientation: Int = LinearLayoutManager.VERTICAL,
    stackFromEnd: Boolean = false,
): LinearLayoutManager = LinearLayoutManager(this, orientation, false).apply {
    this.stackFromEnd = stackFromEnd
}

@SuppressLint("WrongConstant")
fun Context.gridLayoutManager(
    spanCount: Int,
    orientation: Int = GridLayoutManager.VERTICAL,
): GridLayoutManager = GridLayoutManager(this, spanCount, orientation, false)

fun Context.flexboxLayoutManager(
    flexDirection: Int = FlexDirection.ROW,
    flexWrap: Int = FlexWrap.WRAP,
    justifyContent: Int = JustifyContent.FLEX_START,
): FlexboxLayoutManager = FlexboxLayoutManager(this, flexDirection, flexWrap).apply {
    this.justifyContent = justifyContent
}

fun Context.staggeredGridLayoutManager(
    spanCount: Int,
    orientation: Int = StaggeredGridLayoutManager.VERTICAL,
): StaggeredGridLayoutManager = StaggeredGridLayoutManager(spanCount, orientation)

fun <T> MutableList<T>.mutableListInsert(vararg data: T): MutableList<T> {
    data.forEach {
        this.add(it)
    }
    return this
}