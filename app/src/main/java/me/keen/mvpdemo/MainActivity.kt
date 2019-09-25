package me.keen.mvpdemo

import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import me.keen.baselibs.base.BaseMvpActivity
import me.keen.mvpdemo.http.Banner

class MainActivity : BaseMvpActivity<MainContract.View, MainPresenter>(), MainContract.View {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun createPresenter(): MainPresenter = MainPresenter()

    override fun start() {
        btn_get_banner.setOnClickListener { mPresenter.getBanner() }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showBannerList(bannerList: MutableList<Banner>) {
        Glide.with(this).load(bannerList[1].imagePath).into(iv_banner)
        tv_hello.text = bannerList[0].imagePath
    }



}
