package com.thoughtworks.moments.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

  private const val BASE_URL = "http://10.0.2.2:2727/"
  private var _instance: Retrofit? = null

  val instance: Retrofit
    get() {
      if (_instance == null) {
        _instance = Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(MoshiConverterFactory.create())
          .build()
      }
      return _instance!!
    }
}