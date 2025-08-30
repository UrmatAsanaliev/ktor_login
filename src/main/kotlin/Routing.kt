package com.nonmagis

import com.nonmagis.data.model.LoginModel
import com.nonmagis.domain.usecase.LoginUseCase
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.contentType
import io.ktor.server.request.receive
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.util.UUID

fun Application.configureRouting(useCase: LoginUseCase) {

    routing {
        get("/users") {
            val users = useCase.getUsers()
            call.respond(HttpStatusCode.OK, users ?: emptyList())
        }
        post("/register") {
            val contentType = call.request.contentType()

            val loginRequest = when {
                contentType.match(ContentType.Application.Json) -> {
                    call.receive<LoginModel>()
                }

                contentType.match(ContentType.Application.FormUrlEncoded) ||
                        contentType.match(ContentType.MultiPart.FormData) -> {
                    val params = call.receiveParameters()
                    LoginModel(
                        email = params["email"] ?: "",
                        password = params["password"] ?: "",
                    )
                }
                else -> {
                    call.respond(HttpStatusCode.UnsupportedMediaType,
                        "Я не поддерживаю такой типа данных! Иди нахуй!!!")
                    return@post
                }
            }

            val isSuccess = useCase.createUser(loginRequest)
            if (isSuccess) {
                call.respond(HttpStatusCode.OK, "Success registration")
            } else {
                call.respond(HttpStatusCode.Conflict, "Registration failed!")
            }
        }


        post("/login") {
            val contentType = call.request.contentType()

            val loginRequest = when {
                contentType.match(ContentType.Application.Json) -> {
                    call.receive<LoginModel>()
                }

                contentType.match(ContentType.Application.FormUrlEncoded) ||
                        contentType.match(ContentType.MultiPart.FormData) -> {
                    val params = call.receiveParameters()
                    LoginModel(
                        email = params["email"] ?: "",
                        password = params["password"] ?: "",
                    )
                }
                else -> {
                    call.respond(HttpStatusCode.UnsupportedMediaType,
                        "Я не поддерживаю такой типа данных! Иди нахуй!!!")
                    return@post
                }
            }

            if (useCase.getUserById(loginRequest.email) != null) {
                call.respond(HttpStatusCode.OK, Token(token = UUID.randomUUID().toString()))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid Email")
            }
        }
    }
}


@Serializable
data class Token(
    val token: String,
)