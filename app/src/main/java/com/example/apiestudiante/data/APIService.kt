package com.example.apiestudiante.data

import com.example.apiestudiante.data.models.CarrerasResponse
import com.example.apiestudiante.data.models.Data
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("{endpoint}")
    suspend fun listaCarreras(
        @Path("endpoint") endpoint: String,
        @Query("name") searchQuery: String? = null
    ): Response<CarrerasResponse>

    @POST("{endpoint}")
    suspend fun saveCarrera(@Path("endpoint") endpoint: String, @Body city: Data): Response<Data>

    @PUT("{endpoint}/{id}")
    suspend fun updateCarrera(@Path("endpoint") endpoint: String, @Path("id") id: Int, @Body city: Data ): Response<Data>

    @DELETE("{endpoint}/{id}")
    suspend fun deleteCarrera(@Path("endpoint") endpoint: String, @Path("id") id:Int): Response<Void>

    @GET("{endpoint}/{id}")
    suspend fun showCarrera(@Path("endpoint") endpoint: String, @Path("id") id: Int): Response<Data>

}