package com.example.exam.di

import com.example.exam.data.Repository
import com.example.exam.data.RepositoryImpl
import com.example.exam.data.retrofit.WeatherRetrofitService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    fun providesRepository() : Repository{
        return RepositoryImpl(provideRetrofit())
    }

    @Provides
    fun provideRetrofit() :WeatherRetrofitService{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(WeatherRetrofitService::class.java)
    }
}