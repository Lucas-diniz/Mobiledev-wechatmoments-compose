package com.thoughtworks.moments.di

import com.thoughtworks.moments.data.remote.NetworkConfig.BASE_URL
import com.thoughtworks.moments.data.remote.repository.MomentRepositoryDefault
import com.thoughtworks.moments.data.remote.service.MomentService
import com.thoughtworks.moments.domain.repository.MomentRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val dataDi = module {

    single<MomentRepository> { MomentRepositoryDefault(get<MomentService>()) }

    single { get<Retrofit>().create(MomentService::class.java) }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().also {
                        it.setLevel(HttpLoggingInterceptor.Level.BASIC)
                    })
                    .build()
            )
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}