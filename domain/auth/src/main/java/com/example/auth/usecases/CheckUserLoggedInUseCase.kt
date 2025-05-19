package com.example.auth.usecases

import com.example.auth.repository.AuthRepository
import javax.inject.Inject

class CheckUserLoggedInUseCase @Inject constructor(private val repository: AuthRepository) {

    operator fun invoke(): Boolean = repository.checkUserLoggedIn()
}