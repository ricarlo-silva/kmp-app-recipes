package br.com.ricarlo.login.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.navOptions
import br.com.ricarlo.designsystem.spacing
import com.ricarlo.designsystem.generated.resources.visibility_24
import com.ricarlo.designsystem.generated.resources.visibility_off
import com.ricarlo.login.generated.resources.Res
import com.ricarlo.login.generated.resources.access_title
import com.ricarlo.login.generated.resources.create_account
import com.ricarlo.login.generated.resources.forgot_password
import com.ricarlo.login.generated.resources.google_login
import com.ricarlo.login.generated.resources.login
import com.ricarlo.login.generated.resources.no_account
import com.ricarlo.login.generated.resources.other_access
import com.ricarlo.login.generated.resources.password
import com.ricarlo.login.generated.resources.type_email
import com.ricarlo.login.generated.resources.type_password
import com.ricarlo.login.generated.resources.username
import com.ricarlo.designsystem.generated.resources.Res as DesignSystemRes
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<LoginViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.action.collect {
            navController.navigate(it, navOptions {
//                popUpTo("login") {
//                    inclusive = true
//                }
            })
        }
    }

    LoginContent(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier,
    )
}

@Composable
fun LoginContent(
    state: LoginState,
    onAction: (LoginAction) -> Unit = { },
    modifier: Modifier = Modifier,
) {
    var username by remember { mutableStateOf(state.username) }
    var password by remember { mutableStateOf(state.password) }

    LaunchedEffect(state.username, state.password) {
        username = state.username
        password = state.password
    }

    val isLoginButtonEnabled by remember {
        derivedStateOf {
            username.isNotBlank() && password.isNotBlank()
        }
    }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(vertical = MaterialTheme.spacing.extraLarge),
        ) {
            Text(
                text = stringResource(Res.string.access_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraLarge))

        Column(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.large)
        ) {
            Text(
                text = stringResource(Res.string.username),
                style = MaterialTheme.typography.bodyMedium,
            )
            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                    onAction(LoginAction.UsernameChanged(it))
                },
                singleLine = true,
                enabled = !state.isLoading,
                placeholder = { Text(stringResource(Res.string.type_email)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

            Text(
                text = stringResource(Res.string.password),
                style = MaterialTheme.typography.bodyMedium
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    onAction(LoginAction.PasswordChanged(it))
                },
                placeholder = { Text(stringResource(Res.string.type_password)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) DesignSystemRes.drawable.visibility_24 else DesignSystemRes.drawable.visibility_off
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = {passwordVisible = !passwordVisible}){
                        Icon(painter = painterResource(image), description)
                    }
                },
                singleLine = true,
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

            TextButton(
                onClick = { onAction(LoginAction.ForgotPasswordClicked) },
                enabled = !state.isLoading,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = stringResource(Res.string.forgot_password))
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))

            Button(
                onClick = { onAction(LoginAction.LoginClicked) },
                enabled = isLoginButtonEnabled && !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.extraLarge,
            ) {
                Text(stringResource(Res.string.login))
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                )
                Text(
                    text = stringResource(Res.string.other_access),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                    style = MaterialTheme.typography.bodySmall
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

            OutlinedButton(
                onClick = {
                    onAction(LoginAction.GoogleLoginClicked)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.small,
                enabled = !state.isLoading,
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                ),
            ) {
                Icon(
                    imageVector = Icons.Outlined.MailOutline,
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                Text(text = stringResource(Res.string.google_login))
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                )
                Text(
                    text = stringResource(Res.string.no_account),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                    style = MaterialTheme.typography.bodySmall
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

            OutlinedButton(
                onClick = {
                    onAction(LoginAction.SignupClicked)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !state.isLoading,
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.1f
                    )
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            ) {
                Text(text = stringResource(Res.string.create_account))
            }
        }
    }
}
