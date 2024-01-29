package br.edu.ifpb.sunflower

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import br.edu.ifpb.sunflower.data.ImagesSource
import br.edu.ifpb.sunflower.models.ImageModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(page: MutableState<Int>) {
    var openDialog by remember { mutableStateOf(false) }
    var profileImage by remember { mutableStateOf(ImageModel(R.drawable.icon1, R.string.image_icon1)) }
    when {
        openDialog -> {
            ProfileImageDialog({
                openDialog = false
                profileImage = it
            })
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
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
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Green, CircleShape)
                    .background(Color.Gray)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        var text by remember { mutableStateOf("") }
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Nome") }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                // TODO: Ismael Marinho
                page.value = 3
            }
        ) {
            Text(text = stringResource(R.string.Go))
        }
    }
}

@Composable
fun ProfileImageDialog(
    setProfileImage: (ImageModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = { /*TODO*/ }) {
       Card(
          modifier = modifier
              .fillMaxWidth()
              .height(400.dp)
              .padding(16.dp),
           shape = RoundedCornerShape(16.dp)
       ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(ImagesSource.images) {
                    ProfileIcon(setProfileImage, it)
                }
            }
       }
    }
}

@Composable
fun ProfileIcon(
    setProfileImage: (ImageModel) -> Unit,
    image: ImageModel,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
           setProfileImage(image)
        },
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        )
    ) {
        Image(
            painter = painterResource(image.image),
            contentDescription = stringResource(image.content)
        )
    }
}
