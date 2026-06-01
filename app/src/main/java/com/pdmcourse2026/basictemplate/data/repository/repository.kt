package com.pdmcourse2026.basictemplate.data.repository

import com.pdmcourse2026.basictemplate.data.api.KtorClient
import com.pdmcourse2026.basictemplate.model.VoteRequest
import com.pdmcourse2026.basictemplate.model.options
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

interface RankeUcaApi {
    suspend fun getOptions(): Result<List<options>>
    suspend fun vote(optionId: Int): Result<String>
}

class RankeUcaApiImpl : RankeUcaApi {
    override suspend fun getOptions(): Result<List<options>> {
        return try {
            val response: List<options> = KtorClient.client.get("/options").body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun vote(optionId: Int): Result<String> {
        return try {
            val response: String = KtorClient.client.post("/vote") {
                setBody(VoteRequest(optionId))
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


