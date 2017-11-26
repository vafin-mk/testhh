package test.hh.server

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerConnector {
  val BASE_URL = "https://api.hh.ru/"
  val api : HeadHunterApi

  init {
    api = Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(OkHttpClient.Builder().build())
      .build()
      .create(HeadHunterApi::class.java)
  }
}