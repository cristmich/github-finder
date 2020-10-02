package com.cm.github.url

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import com.cm.github.Constants
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class URLFactory {
    var TIMEOUT = 30

    fun retrofit(): Retrofit {
        val gson = GsonBuilder().addSerializationExclusionStrategy(object : ExclusionStrategy {
            override fun shouldSkipField(fieldAttributes: FieldAttributes): Boolean {
                val expose = fieldAttributes.getAnnotation(Expose::class.java)
                return expose != null && !expose.serialize
            }

            override fun shouldSkipClass(aClass: Class<*>): Boolean {
                return false
            }
        }).addDeserializationExclusionStrategy(object : ExclusionStrategy {
            override fun shouldSkipField(fieldAttributes: FieldAttributes): Boolean {
                val expose = fieldAttributes.getAnnotation(Expose::class.java)
                return expose != null && !expose.deserialize
            }

            override fun shouldSkipClass(aClass: Class<*>): Boolean {
                return false
            }
        }).create()
        return Retrofit.Builder()
            .baseUrl(Constants().url)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(URLFactory().getClient())
            .build()
    }

    fun getClient(): OkHttpClient {
        val client = OkHttpClient()
        val connectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .build()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return client.newBuilder()
            .addInterceptor(interceptor)
            .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            .connectionSpecs(listOf(connectionSpec))
            .build()
    }
}