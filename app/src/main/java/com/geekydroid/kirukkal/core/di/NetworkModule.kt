package com.geekydroid.kirukkal.core.di


import com.geekydroid.kirukkal.BASE_URL
import com.geekydroid.kirukkal.core.network.RoomApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder()
        .setLenient()
        .create()


    @Provides
    @Singleton
    fun providesNetworkClient(okHttpClient: OkHttpClient, gson:Gson) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient() : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        val client = OkHttpClient.Builder()
        return client.addInterceptor(loggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun providesRoomApiService(retrofit: Retrofit) : RoomApiService = retrofit.create(RoomApiService::class.java)

}