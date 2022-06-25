package com.gdg.chicpick.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdg.chicpick.login.model.User
import com.gdg.chicpick.login.model.repository.LoginRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    var loginJob: Job? = null

    private val _loginUser = MutableLiveData<User>()
    val loginUser : LiveData<User> get() = _loginUser

    private val _loginError = MutableLiveData<Throwable>()
    val loginError : LiveData<Throwable> get() = _loginError

    fun login(
        email: String,
        password: String
    ) {
        if(loginJob?.isActive != true) {
            loginJob = viewModelScope.launch {
                try {
                    _loginUser.postValue(loginRepository.login(email, password))
                } catch (e: Exception) {
                    _loginError.postValue(e)
                }
            }
        }
    }
}