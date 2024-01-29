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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import br.edu.ifpb.sunflower.games.puzzle.Tela1
import br.edu.ifpb.sunflower.games.sequenciacores.MenuInicial
import br.edu.ifpb.sunflower.models.ImageModel

@Composable
fun Home() {
    Column {
        Top()
        Spacer(modifier = Modifier.height(50.dp))
        Body()
    }
}

@Composable
fun Top() {
    val level = 1
    var openDialog by remember { mutableStateOf(false) }
    var profileImage by remember { mutableStateOf(ImageModel(R.drawable.icon1, R.string.image_icon1)) }
    when {
        openDialog -> {
            ProfileDialog(profileImage, {
                openDialog = false
                profileImage = it
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
            },
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Image(
                painter = painterResource(profileImage.image),
                contentDescription = stringResource(profileImage.content),
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Text(
                    text = stringResource(R.string.profile),
                    color = Color.Black
                )
                Text(
                    text = stringResource(R.string.level) + " " + level,
                    color = Color.Black
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
               modifier = Modifier.size(width = 100.dp, height = 100.dp)
           ) {
               Text("Card")
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

@Composable
fun ProfileDialog(
    profileIcon: ImageModel,
    chooseIcon:(ImageModel) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var openIconDialog by remember { mutableStateOf(false) }
    var profileImage by remember { mutableStateOf(profileIcon) }
    when {
        openIconDialog-> {
            ProfileImageDialog({
                openIconDialog = false
                profileImage = it
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
                       painter = painterResource(profileImage.image),
                       contentDescription = stringResource(profileImage.content),
                       modifier = Modifier
                           .size(150.dp)
                           .clip(CircleShape)
                           .border(2.dp, Color.Green, CircleShape)
                           .background(Color.Gray)
                   )
               }
               Spacer(modifier = Modifier.height(10.dp))
               Text(text = "Name: ")
               Spacer(modifier = Modifier.height(10.dp))
               Text(text = "Level: ")
           }
       }
   }
}

