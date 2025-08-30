package com.nonmagis

import com.nonmagis.data.repository.LoginRepositoryImpl
import com.nonmagis.domain.usecase.LoginUseCase
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val repo = LoginRepositoryImpl()
    val useCase = LoginUseCase(repo)

    configureSerialization()
    configureSecurity()
    configureRouting(useCase)
}
