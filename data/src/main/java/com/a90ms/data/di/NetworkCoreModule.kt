package com.a90ms.data.di

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.a90ms.common.quit
import com.a90ms.data.BuildConfig
import com.a90ms.data.api.Const.DEFAULT_TIME_OUT
import com.a90ms.data.api.Const.HOST_NAME_CUR
import com.a90ms.data.api.Const.HOST_URL
import com.a90ms.data.api.Const.SYNC_INTERVAL
import com.a90ms.data.api.ErrorCode.ERROR_400
import com.a90ms.data.api.ErrorCode.ERROR_401
import com.a90ms.data.api.ErrorCode.ERROR_404
import com.a90ms.data.api.ErrorCode.ERROR_500
import com.a90ms.data.api.ServerInspectionResponse
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkCoreModule {

    private var currentAt = 0L

    @Provides
    @Named(HOST_NAME_CUR)
    fun provideHostWeather() = HOST_NAME_CUR

    @Provides
    @Singleton
    fun provideObjMatter(): ObjectMapper = ObjectMapper().apply {
        registerKotlinModule()
        registerModule(SimpleModule())
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    @Provides
    @Singleton
    fun provideConverterFactory(objectMapper: ObjectMapper): JacksonConverterFactory =
        JacksonConverterFactory.create(objectMapper)

    private fun checkResponseAndReturn(response: Response, context: Context): Response {
        if (!response.isSuccessful) {
            handleServiceError(response, context)
        }
        return response
    }

    @Synchronized
    private fun handleServiceError(response: Response, context: Context) {
        fun showNetworkErrorToast() = showNetworkErrorToast(response, context)

        val current = System.currentTimeMillis()
        if (current - currentAt < SYNC_INTERVAL) return

        currentAt = System.currentTimeMillis()
        try {
            val inspectionResponse = Gson().fromJson(
                response.body?.source()?.readUtf8(),
                ServerInspectionResponse::class.java
            )

            when (inspectionResponse.cod) {
                ERROR_400, ERROR_401, ERROR_404 -> {
                    Handler(Looper.getMainLooper()).post {
                        val message = inspectionResponse.message
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
                ERROR_500 -> {
                    Handler(Looper.getMainLooper()).post {
                        val message = inspectionResponse.message
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                    context.quit()
                }
                else -> {
                    showNetworkErrorToast()
                }
            }
        } catch (e: Exception) {
            showNetworkErrorToast()
        }
    }

    private fun showNetworkErrorToast(response: Response, context: Context) {
        Handler(Looper.getMainLooper()).post {
            val message = response.message.ifBlank { "????????? ????????? ?????????????????????." }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    @Named(HOST_NAME_CUR)
    fun provideSimpleClient(
        @ApplicationContext context: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                Interceptor { chain ->
                    val requestBuilder = chain.request().newBuilder()
                    checkResponseAndReturn(
                        chain.proceed(requestBuilder.build()),
                        context
                    )
                }
            )
            .addNetworkInterceptor(httpLoggingInterceptor)
            .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
        return if (BuildConfig.DEBUG) {
            okHttpClient.build()
            okHttpClient.addNetworkInterceptor(StethoInterceptor()).build()
        } else {
            okHttpClient.build()
        }
    }

    @Provides
    @Singleton
    @Named(HOST_NAME_CUR)
    fun provideRefreshRetrofitBuilder(
        jacksonConverterFactory: JacksonConverterFactory,
        @Named(HOST_NAME_CUR) okHttpClient: OkHttpClient,
    ): Retrofit.Builder = Retrofit
        .Builder()
        .baseUrl(HOST_URL)
        .client(okHttpClient)
        .addConverterFactory(jacksonConverterFactory)
}
