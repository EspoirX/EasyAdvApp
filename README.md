# EasyAdvApp
广告封装

EasyAdv 封装了 穿山甲融合 sdk ，旨在使用广告时可以更加 Easy。

## 可以解决什么问题：  
1. 统一 API，一句话调用。
2. 与业务分离，不需要关注广告实现，让你可以只关心业务开发
3. 全局监听各种广告回调，可以统一处理一些埋点之类的操作，不需要写得到处都是。
4. 总之，感觉还行。。

## 用法：

### 初始化
```kotlin
EasyAdv.init(this)
    .sdkConfig {
        context = this@MyApp
        appId = "KFC_V_WO_50"
        appName = "EasyAdvApp"
        userId = "123456"
        timeOutMillis = 3000 
    }
    .setGlobalAdvConfig {
        enableHotSplashAdv = true
        hotSplashAdvStrategy = TTHotSplashAdvStrategy()
    }
    .setPlatform(TTAdSdkPlatform())
    .setSplashAdvEngine(TTSplashAdvEngine())
    .apply()
```

如示例代码，通过 init 方法初始化，初始化后可配置各种配置，下面一一说明：  

#### sdkConfig
穿山甲 sdk 初始化时需要的参数，应该都知道吧.  
其中 userId 是自己用户系统的 userId，因为初始化或者一些广告加载的时候需要传，当然看情况，不是必要参数。  
timeOutMillis 是sdk初始化超时时间，影响的是冷启动开屏广告，因为如果网络问题或者其他问题导致sdk初始化太久的话，不可能一直卡在开屏页，
所以加个超时逻辑，不设置就是没有这逻辑。

#### setGlobalAdvConfig
配置一些全局的广告配置：  
```kotlin
class GlobalAdvConfig {
    var splashAdvListener: SplashAdvListener? = null
    var fullScreenVideoAdvListener: FullScreenVideoAdvListener? = null
    var bannerAdvListener: BannerAdvListener? = null
    var feedAdvListener: FeedAdvListener? = null
    var rewardVideoAdvListener: RewardVideoAdvListener? = null
    var userId: String = ""
    var enableHotSplashAdv = false
    var hotSplashAdvStrategy: IHotSplashAdvStrategy? = null
}
```

1. splashAdvListener 全局的开屏页广告回调监听
2. fullScreenVideoAdvListener 全局的插屏广告回调监听
3. bannerAdvListener 全局的Banner广告回调监听
4. feedAdvListener 全局的信息流广告回调监听
5. rewardVideoAdvListener 全局的激励广告回调监听
6. userId 跟 sdkConfig 那说的一个意思
7. enableHotSplashAdv 是否运行热启动开屏广告
8. hotSplashAdvStrategy 热启动开屏广告实现方案，要结合 enableHotSplashAdv 为 true 才会生效

#### setPlatform 设置一个 ISdkPlatform，广告sdk 将实现该接口进行初始化

```kotlin
interface ISdkPlatform {
    fun initAdvSdk(
        builder: AdvSDKBuilder,
        callback: AdvSdkInitCallback
    )
}
```

### setSplashAdvEngine ...
```kotlin
setSplashAdvEngine(engine: ISplashAdvEngine)
setFullScreenVideoAdvEngine(engine: IFullScreenVideoAdvEngine)
setBannerAdvEngine(engine: IBannerAdvEngine)
setFeedAdvEngine(engine: IFeedAdvEngine)
setRewardVideoEngine(engine: IRewardVideoAdvEngine)
```

对应各种广告的实现，实现对应的接口即可。最后调用 apply() 完成初始化.

### 显示广告
以 开屏广告 为例，其他类型差不多。

1. 首先实现 ISplashAdvEngine 接口，完成开屏广告具体的实现，主要是实现 showSplashAdv 方法（完整示例可以查看代码中的 TTSplashAdvEngine）
```kotlin
class TTSplashAdvEngine : ISplashAdvEngine {
    override fun showSplashAdv(config: SplashAdvConfig) {
        val adNativeLoader = TTAdSdk.getAdManager().createAdNative(act)
        val adSlot = AdSlot.Builder()
            .setCodeId(config.codeId)
            .setImageAcceptedSize(config.width, config.height)
            .build()
        adNativeLoader.loadSplashAd(adSlot, object : TTAdNative.SplashAdListener {
            //...
        }
    }
}
```

然后在开屏页直接调用展示即可：

```kotlin
EasyAdv.splashConfig()
    .setCodeId("KFC_V_WO_50")
    .setActivity(this)
    .setWidth(getScreenWidth())
    .setHeight(getScreenHeight())
    .setContainer(findViewById<FrameLayout>(R.id.adLayout))
    .setSplashAdvListener(onError = { _, _ ->
        toMain()
    }, onAdClicked = { _, _ ->
        toMain()
    }, onAdSkip = {
        toMain()
    }, onAdTimeOver = {
       toMain()
    })
    .showSplashAdv()
```

如上，设置广告的 codeId，当前act（如果不是 act 的上下文，广告会显示不出来），常规宽高，广告 ViewGroup，各种回调等，最后调用 showSplashAdv 即可。

在调用开屏广告的时候如果 sdk 还没初始化完成，showSplashAdv 会一直等待 sdk 初始化完成才会执行，所以不用担心时序问题。  
当 sdk 初始化失败或者超时的时候，会回调 onError。回调方法除了常规的以外，还提供了 DSL 的方式（如上示例），要哪个再写哪个，提高代码的简洁度。

** 热启动开屏广告 **

先在初始化的时候将 enableHotSplashAdv 设为 true，然后实现 IHotSplashAdvStrategy 接口。

```kotlin
interface IHotSplashAdvStrategy {
    fun getCodeId(): String

    fun showRequirement(activity: Activity): Boolean

    fun showHotSplashAdv(activity: Activity, codeId: String)
}
```
1. getCodeId 就是热启动广告的 codeId
2. showRequirement 展示条件，比如说隔10分钟展示一次，哪些页面展示哪些不需要等，要是没条件直接返回 true 就行
3. showHotSplashAdv 显示具体实现

举例（演示了每隔10分钟显示一次热启动广告）：

```kotlin
class TTHotSplashAdvStrategy : IHotSplashAdvStrategy {

    private var lastShowTime = 0L
    private var ttHotSplashAdvDialog: TTHotSplashAdvDialog? = null

    override fun getCodeId(): String = "KFC_V_WO_50"

    override fun showRequirement(activity: Activity): Boolean {
        return activity !is SplashActivity
                && System.currentTimeMillis() - lastShowTime > 60 * 60 * 1000
    }

    override fun showHotSplashAdv(activity: Activity, codeId: String) {
        lastShowTime = System.currentTimeMillis()
        ttHotSplashAdvDialog = TTHotSplashAdvDialog(activity, onShow = { container ->
            EasyAdv.splashConfig()
                .setCodeId(codeId)
                .setActivity(activity)
                .setWidth(activity.getScreenWidth())
                .setHeight(activity.getScreenHeight())
                .setContainer(container)
                .setSplashAdvListener(onError = { _, _ ->
                    ttHotSplashAdvDialog?.dismiss()
                }, onAdClicked = { _, _ ->
                    ttHotSplashAdvDialog?.dismiss()
                }, onAdSkip = {
                    ttHotSplashAdvDialog?.dismiss()
                }, onAdTimeOver = {
                    ttHotSplashAdvDialog?.dismiss()
                })
                .showSplashAdv()
        }, onDismiss = {})
        ttHotSplashAdvDialog?.show()
    }
}
```

### 激励广告说明

广告流程大体就跟上面说的差不多，其他广告也一样，激励广告在显示时可能需要先获取接口返回的一些参数 id，在调用的时候传给
广告sdk 。所以激励广告多了一个拦截器逻辑用来处理这类情况。

```kotlin
EasyAdv.rewardVideoConfig()
    .setCodeId("KFC_V_WO_50")
    .setXXX()
    ...
    .addInterceptor(object : AdvInterceptor() {
        override fun getTag(): String = "RewardInterceptor"

        override fun process(map: HashMap<String, String>?, callback: AdvInterceptCallback) {
            super.process(map, callback)
            //比如一顿操作后获取到各种参数，然后把他设置到 map 里面即可
            map?.put(MediationConstant.CUSTOM_DATA_KEY_GROMORE_EXTRA, "KFC_V_WO_50")
            map?.put(MediationConstant.ADN_PANGLE, "KFC_V_WO_50")
            map?.put(MediationConstant.ADN_KS, "KFC_V_WO_50")
            map?.put(MediationConstant.ADN_GDT, "KFC_V_WO_50")
            callback.onNext(map)
        }
    })
```

喜欢的可以点个星，有负责的场景可以提出意见，我会进行完善。



