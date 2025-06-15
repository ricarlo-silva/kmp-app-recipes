package br.com.ricarlo.login.presentation

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _action = MutableSharedFlow<String>()
    val action = _action.asSharedFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.UsernameChanged -> onUsernameChange(action.username)
            is LoginAction.PasswordChanged -> onPasswordChange(action.password)
            LoginAction.LoginClicked -> onLoginClick()
            LoginAction.ForgotPasswordClicked -> onForgotPasswordClick()
            LoginAction.GoogleLoginClicked -> onGoogleLoginClick()
            LoginAction.SignupClicked -> onSignupClick()
        }
    }

    private fun onUsernameChange(username: String) {
        _state.update {
            it.copy(username = username)
        }
    }

    private fun onPasswordChange(password: String) {
        _state.update {
            it.copy(password = password)
        }
    }

    private fun onLoginClick() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            delay(1000)
            _state.update {
                it.copy(isLoading = false)
            }
            _action.emit("home")
        }
    }

    private fun onSignupClick() {
        viewModelScope.launch {
            // TODO: Navigate to signup screen
            _action.emit("home")
        }
    }

    private fun onForgotPasswordClick() {
        viewModelScope.launch {
            // TODO: Navigate to forgot password screen
            _action.emit("home")
        }
    }

    private fun onGoogleLoginClick() {
        viewModelScope.launch {
            // TODO: Handle Google login
            _action.emit("home")
        }
    }
}

@Stable
data class LoginState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
)

sealed class LoginAction {
    data class UsernameChanged(val username: String) : LoginAction()
    data class PasswordChanged(val password: String) : LoginAction()
    data object LoginClicked : LoginAction()
    data object SignupClicked : LoginAction()
    data object ForgotPasswordClicked : LoginAction()
    data object GoogleLoginClicked : LoginAction()
}
