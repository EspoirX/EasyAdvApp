package com.espoir.easyadv.interceptor

abstract class AdvInterceptor {
    abstract fun getTag(): String
    open fun process(map: HashMap<String, String>?, callback: AdvInterceptCallback) {}
}

interface AdvInterceptCallback {
    /**
     * 执行下一个
     */
    fun onNext(map: HashMap<String, String>?)

    /**
     * 中断
     * msg:可以添加 msg
     */
    fun onInterrupt(msg: String?)
}