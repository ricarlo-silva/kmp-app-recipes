package br.com.ricarlo.login.presentation

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class LoginViewModel(
    val permissionsController: PermissionsController
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _action = MutableSharedFlow<String>()
    val action = _action.asSharedFlow()

    init {
        requestPermission(Permission.REMOTE_NOTIFICATION)
    }

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.UsernameChanged -> onUsernameChange(action.username)
            is LoginAction.PasswordChanged -> onPasswordChange(action.password)
            is LoginAction.LoginClicked -> onLoginClick()
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
            delay(10000)
            _state.update {
                it.copy(isLoading = false)
            }
            _action.emit("navigate")
        }
    }

    private fun requestPermission(permission: Permission) {
        viewModelScope.launch {
            try {
                permissionsController.getPermissionState(permission)
                    .also { _action.emit("pre provide $it") }

                permissionsController.providePermission(permission)
                _action.emit("onSuccess")
            } catch (deniedAlwaysException: DeniedAlwaysException) {
                _action.emit("onDeniedAlways: ${deniedAlwaysException.message}")
            } catch (deniedException: DeniedException) {
                _action.emit("onDenied: ${deniedException.message}")
            } finally {
                permissionsController.getPermissionState(permission)
                    .also { _action.emit("post provide $it") }
            }
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
}
