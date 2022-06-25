package com.gdg.chicpick.login.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gdg.chicpick.databinding.ActivityLoginBinding
import com.gdg.chicpick.login.LoginInstances
import com.gdg.chicpick.login.viewmodel.LoginViewModel
import com.gdg.chicpick.result.view.contract.ResultActivityContract
import com.gdg.chicpick.survey.view.SurveyActivity

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val resultActivityContract = registerForActivityResult(ResultActivityContract()) {}

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
                password = "1234" // editTextPassword.text.toString()
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
            if (loginUser.hasSurvey) {
                resultActivityContract.launch(loginUser.id)
                finish()
            } else {
                startActivity(Intent(this@LoginActivity, SurveyActivity::class.java).apply {
                    putExtra(EXTRA_ID, loginUser.id)
                })
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_ID = "Extra.Id"
    }
}