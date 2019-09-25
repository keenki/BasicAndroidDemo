package me.keen.baselibs.base

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import kotlinx.android.synthetic.main.activity_base_title.*
import kotlinx.android.synthetic.main.base_toolbar.*
import me.keen.baselibs.R
import me.keen.baselibs.mvp.IPresenter
import me.keen.baselibs.mvp.IView

/**
 * @author xiekeen
 * @date 2019-09-23
 * @desc BaseTitleActivity
 */
abstract class BaseTitleActivity<in V : IView, P : IPresenter<V>> : BaseMvpActivity<V, P>() {

    protected abstract fun getChildLayoutId(): Int
    /**
     * 是否启用返回键
     */
    open fun hasBackIcon(): Boolean = true

    override fun getLayoutId() = R.layout.activity_base_title

    override fun initView() {
        super.initView()
        base_container.addView(layoutInflater.inflate(getChildLayoutId(), null))
        base_toolbar.title = ""
        setSupportActionBar(base_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(hasBackIcon())
    }

    /**
     * 设置 Title
     */
    fun setBaseTitle(title: String) {
        base_title_tv.text = title
    }

    /**
     * 设置 Title
     */
    fun setBaseTitleText(@StringRes resId: Int) {
        base_title_tv.setText(resId)
    }

    /**
     * 设置 Title 颜色
     */
    fun setBaseTitleColor(@ColorRes color: Int) {
        base_title_tv.setTextColor(resources.getColor(color))
    }

}