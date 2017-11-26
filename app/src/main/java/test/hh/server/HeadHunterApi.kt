package test.hh.server

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HeadHunterApi {
  @GET("vacancies") fun vacancies(@Query("text") searchText: String): Call<SearchResult>
}