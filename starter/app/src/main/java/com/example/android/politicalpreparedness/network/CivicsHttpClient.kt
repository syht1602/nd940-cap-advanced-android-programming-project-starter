package com.example.android.politicalpreparedness.network

import com.example.android.politicalpreparedness.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class CivicsHttpClient : OkHttpClient() {

    companion object {

        //        const val API_KEY = "" //TODO: Place your API Key Here
//        const val API_KEY =
//            "AIzaSyB2gWCL7e3DrDWahA57fWR5ewe2cT2QH_8" //TODO: Place your API Key Here
//        const val API_KEY =
//            "AIzaSyBiXAug-B7BN1IuBfXAyEUqMJTjX4_xOcU" //TODO: Place your API Key Here

        private val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        fun getClient(): OkHttpClient {
            return Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val url = original
                        .url
                        .newBuilder()
                        .addQueryParameter("key", BuildConfig.API_KEY)
                        .build()
                    val request = original
                        .newBuilder()
                        .url(url)
                        .build()
                    chain.proceed(request)
                }.addInterceptor(interceptor)
                .build()
        }

    }

}