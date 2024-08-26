package com.github.smalljooj.sunflowerapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.smalljooj.sunflowerapp.data.types.model.ImageModel
import com.github.smalljooj.sunflowerapp.data.types.model.User
import com.github.smalljooj.sunflowerapp.ui.profile.ProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.smalljooj.sunflowerapp.R
import com.github.smalljooj.sunflowerapp.ui.commonComposables.QuestionDialog
import com.github.smalljooj.sunflowerapp.ui.profile.ProfileImageDialog

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    goToSnakeGame: () -> Unit,
    goToPuzzleGame: () -> Unit,
    goToColorsGame: () -> Unit,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
    val userState by viewModel.userState.collectAsState()
    if (viewModel.openQuestionDialog) {
        QuestionDialog(question = viewModel.question, send = {
            viewModel.answer(it)
            viewModel.updateOpenQuestionDialog(false)
        })
    }
    if (viewModel.openDialog) {
        ProfileDialog(
            user = userState,
            onDismiss = { viewModel.updateOpenDialog(false) }
        )
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        viewModel.updateOpenDialog(true)
                    },
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Image(
                        painter = painterResource(userState.image),
                        contentDescription = stringResource(userState.imageTitle),
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Column {
                        Text(
                            text = userState.name,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = stringResource(R.string.level, userState.level),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            GameCard(
                image = ImageModel(R.drawable.snake_game_splash, R.string.snake_game),
                onclick = { goToSnakeGame() },
                width = 300,
                height = 200
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

                GameCard(
                    image = ImageModel(R.drawable.colorsequency, R.string.colorsequency),
                    onclick = { goToColorsGame() },
                    width = 100,
                    height = 100
                )
                Spacer(modifier = Modifier.width(10.dp))
                GameCard(
                    image = ImageModel(R.drawable.puzzle, R.string.puzzle),
                    onclick = { goToPuzzleGame() },
                    width = 100,
                    height = 100
                )
                Spacer(modifier = Modifier.width(10.dp))
                GameCard(
                    image = ImageModel(R.drawable.memorygame, R.string.memorygame),
                    onclick = { /*TODO*/ },
                    width = 100,
                    height = 100
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

@Composable
fun GameCard(
    image: ImageModel,
    onclick: () -> Unit,
    width: Int,
    height: Int,
) {
    Card(
        elevation = CardDefaults.cardElevation( defaultElevation = 6.dp ),
        modifier = Modifier.size(width = width.dp, height = height.dp),
        onClick = onclick
    ) {
        Image(
            painter = painterResource(image.image),
            contentDescription = stringResource(image.content),
            modifier = Modifier
                    .fillMaxWidth(),
            contentScale = ContentScale.Crop
            )
    }
}

@Composable
fun ProfileDialog(
    user: User,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    if (viewModel.openDialog){
        ProfileImageDialog({
            viewModel.updateOpenDialog(false)
            viewModel.updateImage(it.image)
            viewModel.updateTitle(it.content)
        })
    }
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        viewModel.updateOpenDialog(true)
                    },
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Image(
                        painter = painterResource(uiState.image),
                        contentDescription = stringResource(uiState.title),
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Green, CircleShape)
                            .background(Color.Gray)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = uiState.name,
                    label = { Text(stringResource(R.string.name)) },
                    onValueChange = {
                        viewModel.updateName(it)
                    },
                    modifier = Modifier
                        .width(200.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = stringResource(R.string.level, user.level))
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    onClick = {
                        viewModel.updateUser()
                        onDismiss()
                    },
                ) {
                    Text(stringResource(R.string.save))
                }
            }
        }
    }
}
