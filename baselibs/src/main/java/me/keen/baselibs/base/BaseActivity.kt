package me.keen.baselibs.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import me.keen.baselibs.app.BaseApplication
import me.keen.baselibs.utils.CommonUtil
import me.keen.baselibs.utils.KeyBoardUtil
import me.keen.baselibs.utils.StatusBarUtil
import org.greenrobot.eventbus.EventBus


/**
 * @author xiekeen
 * @date 2019-09-23
 * @desc BaseActivity
 */
abstract class BaseActivity : AppCompatActivity() {

    /**
     * 布局文件id
     */
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    /**
     * 初始化数据
     */
    open fun initData() {}

    /**
     * 初始化 View
     */
    abstract fun initView()

    /**
     * 开始请求
     */
    abstract fun start()

    /**
     * 是否使用 EventBus
     */
    open fun useEventBus(): Boolean = false


    /**
     * 获取权限处理类
     */
    protected val rxPermissions: RxPermissions by lazy {
        RxPermissions(this)
    }

    /**
     * 设置状态栏的背景颜色
     */
    fun setStatusBarColor(@ColorInt color: Int) {
        StatusBarUtil.setColor(this, color, 0)
    }

    /**
     * 设置状态栏图标的颜色
     *
     * @param dark true: 黑色  false: 白色
     */
    fun setStatusBarIcon(dark: Boolean) {
        StatusBarUtil.setLightStatusBar(this, dark)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // 强制竖屏
        setContentView(getLayoutId())
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        initView()
        initData()
        start()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_UP) {
            val v = currentFocus
            // 如果不是落在EditText区域，则需要关闭输入法
            if (KeyBoardUtil.isHideKeyboard(v, ev)) {
                KeyBoardUtil.hideKeyBoard(this, v)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        CommonUtil.fixInputMethodManagerLeak(this)
        BaseApplication.getRefWatcher(this)?.watch(this)
    }

}