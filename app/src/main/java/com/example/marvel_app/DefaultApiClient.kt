package com.example.marvel_app

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.util.Date
import java.util.concurrent.TimeUnit

object DefaultApiClient {

    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(authInterceptor())
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder().client(buildOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    private fun authInterceptor() = Interceptor{
            chain ->
        val originalRequest = chain.request()
        val httpUrl = originalRequest.url
        val timestamp = Date().time.toString()
        val hashInput = timestamp + PRIVATE_KEY + PUBLIC_KEY
        val newUrl = httpUrl.newBuilder()
            .addQueryParameter("ts", timestamp)
            .addQueryParameter("apikey", PUBLIC_KEY)
            .addQueryParameter("hash", md5(hashInput))
            .build()
        val newRequest = originalRequest
            .newBuilder()
            .url(newUrl)
            .build()
        return@Interceptor chain.proceed(newRequest)
    }

    private fun md5(hashingInput: String): String {
        val md = MessageDigest.getInstance("MD5")
        val input = hashingInput.toList().joinToString("")
        return BigInteger(1, md.digest(input.toByteArray()))
            .toString(16)
            .padStart(32, '0')
    }

    private const val BASE_URL = "https://gateway.marvel.com/v1/public/"
    private const val PUBLIC_KEY = ""
    private const val PRIVATE_KEY = ""

    @Volatile
    private var INSTANCE: Retrofit? = null

    fun init() {
        INSTANCE = buildRetrofit()
    }

    @Synchronized
    fun getInstance(): Retrofit {
        return synchronized(this) {
            INSTANCE
                ?: throw IllegalArgumentException("You need to initialize ApiClient first in the application class")
        }
    }
}