package com.example.auth.usecases

import com.example.auth.repository.AuthRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val repository: AuthRepository) {

    operator fun invoke() = repository.logOut()
}