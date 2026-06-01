package com.pdmcourse2026.basictemplate.data.api

import android.util.Log
import com.pdmcourse2026.basictemplate.model.options
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
/*
"apiKey":"6cf39f8d-ddac-4b35-9839-32b9f18c8060"

curl https://qjcxdvfzyseuvezacxsd.supabase.co/functions/v1/rankeuca/options \
--header 'Authorization: Bearer 6cf39f8d-ddac-4b35-9839-32b9f18c8060'

[{"id":1,"name":"Cafetería Central","imageUrl":"https://placehold.co/400x300?text=Cafeteria+Central","votes":0},{"id":2,"name":"El Chambre","imageUrl":"https://placehold.co/400x300?text=El+Chambre","votes":0},{"id":3,"name":"El Asado de Yubran","imageUrl":"https://placehold.co/400x300?text=El+Asado+de+Yubran","votes":0},{"id":4,"name":"El Rinconcito","imageUrl":"https://placehold.co/400x300?text=El+Rinconcito","votes":0},{"id":5,"name":"El Delicioso Snack","imageUrl":"https://placehold.co/400x300?text=El+Delicioso+Snack","votes":0},{"id":6,"name":"Chiquiwiki","imageUrl":"https://placehold.co/400x300?text=Chiquiwiki","votes":0},{"id":7,"name":"Los Puercos Hermanos","imageUrl":"https://placehold.co/400x300?text=Los+Puercos+Hermanos","votes":0},{"id":8,"name":"Little Caesar","imageUrl":"https://placehold.co/400x300?text=Little+Caesar","votes":0},{"id":9,"name":"Wendy's","imageUrl":"https://placehold.co/400x300?text=Wendys","votes":0},{"id":10,"name":"Burger King","imageUrl":"https://placehold.co/400x300?text=Burger+King","votes":0},{"id":11,"name":"Papa John's","imageUrl":"https://placehold.co/400x300?text=Papa+Johns","votes":0},{"id":12,"name":"Charlie Boys","imageUrl":"https://placehold.co/400x300?text=Charlie+Boys","votes":0}]


curl https://qjcxdvfzyseuvezacxsd.supabase.co/functions/v1/rankeuca/vote \
--request POST \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer 6cf39f8d-ddac-4b35-9839-32b9f18c8060' \
--data '{
"optionId": 1
}'

{"ok":true,"message":"Vote registered"}
*/
object KtorClient {
  private const val API_KEY = "6cf39f8d-ddac-4b35-9839-32b9f18c8060"
  private const val BASE_URL = "https://qjcxdvfzyseuvezacxsd.supabase.co/functions/v1/rankeuca/"

  val client = HttpClient(OkHttp) {
    // Parseo automático de JSON
    install(ContentNegotiation) {
      json(Json {
        ignoreUnknownKeys = true
      })
    }

    // Plugin de logging
    install(Logging) {
      logger = object : Logger {
        override fun log(message: String) {
          Log.d("KtorClient", message)
        }
      }
      level = LogLevel.ALL
    }

    // Configuración aplicada a todas las peticiones
    defaultRequest {
      url(BASE_URL)
      header(HttpHeaders.Accept, "application/json")
      header(HttpHeaders.Authorization, "Bearer $API_KEY")
    }
  }
}