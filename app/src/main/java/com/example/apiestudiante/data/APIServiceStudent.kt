package com.example.apiestudiante.data

import com.example.apiestudiante.data.models.DataEstudiante
import com.example.apiestudiante.data.models.EstudiantesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface APIServiceStudent {

    @GET("{endpoint}")
    suspend fun listaEstudiantes(
        @Path("endpoint") endpoint: String,
        @Query("name") searchQuery: String? = null
    ): Response<EstudiantesResponse>

    @POST("{endpoint}")
    suspend fun saveEstudiante(@Path("endpoint") endpoint: String, @Body estudiante: DataEstudiante): Response<DataEstudiante>

    @PUT("{endpoint}/{id}")
    suspend fun updateEstudiante(@Path("endpoint") endpoint: String, @Path("id") id: Int, @Body estudiante: DataEstudiante): Response<DataEstudiante>

    @DELETE("{endpoint}/{id}")
    suspend fun deleteEstudiante(@Path("endpoint") endpoint: String, @Path("id") id:Int): Response<Void>

    @GET("{endpoint}/{id}")
    suspend fun showEstudiante(@Path("endpoint") endpoint: String, @Path("id") id: Int): Response<DataEstudiante>
}