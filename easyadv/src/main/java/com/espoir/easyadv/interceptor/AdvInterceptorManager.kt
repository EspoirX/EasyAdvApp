package com.espoir.easyadv.interceptor

class AdvInterceptorManager {
    private var interceptors = mutableListOf<AdvInterceptor>()

    fun attachInterceptors(interceptors: MutableList<AdvInterceptor>) {
        this.interceptors.clear()
        this.interceptors.addAll(interceptors)
    }

    fun handlerInterceptor(map: HashMap<String, String>?, callback: AdvInterceptCallback?) {
        if (interceptors.isEmpty()) {
            callback?.onNext(map)
        } else {
            runCatching {
                doInterceptor(0, map, callback)
            }.onFailure {
                callback?.onInterrupt(it.message)
            }
        }
    }

    private fun doInterceptor(index: Int, map: HashMap<String, String>?, callback: AdvInterceptCallback?) {
        if (index < interceptors.size) {
            val interceptor = interceptors[index]
            doInterceptImpl(interceptor, index, map, callback)
        } else {
            callback?.onNext(map)
        }
    }

    private fun doInterceptImpl(
        interceptor: AdvInterceptor,
        index: Int,
        map: HashMap<String, String>?,
        callback: AdvInterceptCallback?
    ) {
        interceptor.process(map, object : AdvInterceptCallback {
            override fun onNext(map: HashMap<String, String>?) {
                doInterceptor(index + 1, map, callback)
            }

            override fun onInterrupt(msg: String?) {
                callback?.onInterrupt(msg)
            }
        })
    }
}