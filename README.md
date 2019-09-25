# BasicAndroidDemo
Android项目基础依赖，导入baselibs模块就可以创建一个使用了MVP+Retrofit+RxJava的安卓项目。

##使用方法
### 继承BaseApplication
* 在AndroidManifest文件里注册它

` <application
android:name="me.keen.mvpdemo.base.App"/>`

### 使用Retrofit进行网络请求

* 定义请求API的接口

```
interface CustomerApi {
    @GET("banner/json")
    fun getHomeBanner(): Observable<HttpResult<MutableList<Banner>>>
}
```

* 创建Retrofit请求单例，继承RetrofitFactory,并实现下面两个方法

```
object CustomerRetrofit : RetrofitFactory<CustomerApi>() {

    override fun baseUrl() = Constant.BASE_URL

    override fun getService(): Class<CustomerApi> = CustomerApi::class.java
}
```

###  使用MVP架构

* 定义业务的Contract接口

```
interface MainContract {
    interface View : IView {
        fun showBannerList(bannerList: MutableList<Banner>)
    }

    interface Model:IModel{
        fun getBanner(): Observable<HttpResult<MutableList<Banner>>>
    }
}
```

* Presenter

`class MainPresenter : BasePresenter<MainContract.Model, MainContract.View>(){}`

* 在Activity中实现相关方法

`class MainActivity : BaseMvpActivity<MainContract.View, MainPresenter>(), MainContract.View {}`