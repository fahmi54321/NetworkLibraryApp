package com.example.networklibrary

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkProvider{

    //3
    fun provideHttpAdapter():Retrofit{
        return Retrofit.Builder().apply {
            client(provideHttpClient())
            baseUrl(BuildConfig.BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }

    //2
    private fun provideHttpClient():OkHttpClient
    {
        return OkHttpClient.Builder().apply {
            retryOnConnectionFailure(true)
            addInterceptor(provideHttpLoggingInterceptor())
        }.build()
    }

    //1
    private fun provideHttpLoggingInterceptor():HttpLoggingInterceptor
    {
        return HttpLoggingInterceptor().apply {
            level = when(BuildConfig.DEBUG){
                true-> HttpLoggingInterceptor.Level.BODY
                false -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}