package com.gdg.chicpick.login.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gdg.chicpick.databinding.ActivityLoginBinding
import com.gdg.chicpick.login.LoginInstances
import com.gdg.chicpick.login.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<LoginViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return LoginViewModel(loginRepository = LoginInstances.loginRepository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {
        textViewLogin.setOnClickListener {
            viewModel.login(
                email = editTextEmail.text.toString(),
                password = editTextPassword.text.toString()
            )
        }
    }

    private fun initViewModel() = with(viewModel) {
        loginError.observe(this@LoginActivity) {
            Toast.makeText(
                this@LoginActivity,
                it.localizedMessage ?: "Unknown error",
                Toast.LENGTH_SHORT
            ).show()
        }

        loginUser.observe(this@LoginActivity) { loginUser ->
            Toast.makeText(this@LoginActivity, loginUser.id.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}