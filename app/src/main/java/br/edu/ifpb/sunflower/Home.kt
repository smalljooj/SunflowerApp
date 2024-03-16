package br.edu.ifpb.sunflower

import android.content.Intent
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import br.edu.ifpb.sunflower.games.memoria.Memoria
import br.edu.ifpb.sunflower.games.puzzle.Tela1
import br.edu.ifpb.sunflower.games.sequenciacores.MenuInicial
import br.edu.ifpb.sunflower.models.ImageModel
import br.edu.ifpb.sunflower.models.UserModel
import br.edu.ifpb.sunflower.room.User
import br.edu.ifpb.sunflower.room.viewmodels.UserViewModel

@Composable
fun Home(viewModel: UserViewModel) {
    var state  = viewModel.state

    Column {
        Top(viewModel)
        Spacer(modifier = Modifier.height(50.dp))
        Body()
    }
}

@Composable
fun Top(viewModel: UserViewModel) {
    var openDialog by remember { mutableStateOf(false) }
    var state  = viewModel.state
    var user by remember {
        mutableStateOf(
            UserModel(
                name = state.user?.name ?: "",
                level = state.user?.level ?: 1,
                image = ImageModel(state.user?.icon ?: R.drawable.icon1, R.string.image_icon1)
            )
        )
    }
    when {
        openDialog -> {
            ProfileDialog(user, { image: ImageModel, name: String ->
                openDialog = false
                user.image = image
                user.name = name
                viewModel.updateUser(
                    User(
                        id = state.user?.id,
                        name = user.name,
                        icon = user.image.image,
                        level = state.user?.level ?: 1,
                        emotion = state.user?.emotion ?: 0
                        )
                )
            }, {
                openDialog = false
            })
        }
    }
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                openDialog = true
                state = viewModel.state
                user = UserModel(
                    name = state.user?.name ?: "",
                    level = state.user?.level ?: 1,
                    image = ImageModel(state.user?.icon ?: R.drawable.icon1, R.string.image_icon1)
                )
            },
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Image(
                painter = painterResource(state.user?.icon ?: R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(R.string.icon),
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Text(
                    text = state.user?.name ?: "user",
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = stringResource(R.string.level) + " " + state.user?.level ?: "1",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Body() {
    val context = LocalContext.current
    Column(
       modifier = Modifier.fillMaxSize(),
       horizontalAlignment = Alignment.CenterHorizontally
    ) {
       Card(
           elevation = CardDefaults.cardElevation( defaultElevation = 6.dp ),
           modifier = Modifier.size(width = 300.dp, height = 200.dp),
           onClick = {
               val i = Intent(context, MenuInicial::class.java)
               context.startActivity(i)
           }
       ) {
           Image(
               painter = painterResource(R.drawable.sequenciacores),
               contentDescription = null,
               modifier = Modifier
                   .fillMaxWidth(),
               contentScale = ContentScale.Crop
           )
       }
       Spacer(modifier = Modifier.height(10.dp))
       Row(
           horizontalArrangement = Arrangement.Center,
           modifier = Modifier.fillMaxWidth()
       ) {
           Card(
               elevation = CardDefaults.cardElevation( defaultElevation = 6.dp ),
               modifier = Modifier.size(width = 100.dp, height = 100.dp),
               onClick = {
                   val i = Intent(context, Tela1::class.java)
                   context.startActivity(i)
               }
           ) {
               Image(
                   painter = painterResource(R.drawable.puzzle),
                   contentDescription = null,
                   modifier = Modifier
                       .fillMaxWidth(),
                   contentScale = ContentScale.Crop
               )
           }
           Spacer(modifier = Modifier.width(10.dp))
           Card(
               elevation = CardDefaults.cardElevation( defaultElevation = 6.dp ),
               modifier = Modifier.size(width = 100.dp, height = 100.dp),
               onClick = {
                   val i = Intent(context, Memoria::class.java)
                   context.startActivity(i)
               }
           ) {
               Image(
                   painter = painterResource(R.drawable.memoria),
                   contentDescription = null,
                   modifier = Modifier
                       .fillMaxWidth(),
                   contentScale = ContentScale.Crop
               )
           }
           Spacer(modifier = Modifier.width(10.dp))
           Card(
               elevation = CardDefaults.cardElevation( defaultElevation = 6.dp ),
               modifier = Modifier.size(width = 100.dp, height = 100.dp)
           ) {
               Text("Card")
           }
           Spacer(modifier = Modifier.width(10.dp))
       }
   }
}

@Composable
fun GameCard(
    image: ImageModel,
    width: Int,
    height: Int,
    name: String
) {
    Card(
        elevation = CardDefaults.cardElevation( defaultElevation = 6.dp ),
        modifier = Modifier.size(width = width.dp, height = height.dp)
    ) {
        Image(
            painter = painterResource(image.image),
            contentDescription = stringResource(image.content),
            modifier = Modifier
                .clip(RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 10.dp,
                    bottomEnd = 10.dp
                )
            )
        )
        Text(
            text = name,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDialog(
    user: UserModel,
    onChange: (ImageModel, String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var openIconDialog by remember { mutableStateOf(false) }
    var profile by remember {
        mutableStateOf(
            UserModel(
                name = user.name,
                level = user.level,
                image = user.image
            )
        )
    }
    var name  by remember { mutableStateOf(user.name) }
    var nameEdit by remember { mutableStateOf(false) }
    when {
        openIconDialog-> {
            ProfileImageDialog({
                openIconDialog = false
                profile.image = it
            })
        }
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
                       openIconDialog = true
                   },
                   shape = RoundedCornerShape(50.dp),
                   colors = ButtonDefaults.buttonColors(
                       containerColor = Color.Transparent
                   )
               ) {
                   Image(
                       painter = painterResource(profile.image.image),
                       contentDescription = stringResource(profile.image.content),
                       modifier = Modifier
                           .size(150.dp)
                           .clip(CircleShape)
                           .border(2.dp, Color.Green, CircleShape)
                           .background(Color.Gray)
                   )
               }
               Spacer(modifier = Modifier.height(10.dp))
               TextField( 
                   value = name, 
                   label = { Text(stringResource(R.string.name)) },
                   onValueChange = {
                        name = it
                        profile.name = name
                    }, 
                   modifier = Modifier
                        .width(200.dp),
                   keyboardOptions = KeyboardOptions.Default.copy(
                       imeAction = ImeAction.Done
                   )
               )
               Spacer(modifier = Modifier.height(10.dp))
               Text(text = stringResource(R.string.level) + ": " + user.level)
               Spacer(modifier = Modifier.height(10.dp))
               OutlinedButton(
                   onClick = {
                       onChange(profile.image, profile.name)
                   },
               ) {
                   Text(stringResource(R.string.save))
               }
           }
       }
   }
}

