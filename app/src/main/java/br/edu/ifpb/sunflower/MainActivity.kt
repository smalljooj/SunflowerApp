package br.edu.ifpb.sunflower

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.dataStore
import androidx.room.Room
import br.edu.ifpb.sunflower.datastore.AppSettings
import br.edu.ifpb.sunflower.datastore.AppSettingsSerializer
import br.edu.ifpb.sunflower.models.Question
import br.edu.ifpb.sunflower.room.UserDatabase
import br.edu.ifpb.sunflower.room.viewmodels.UserViewModel
import br.edu.ifpb.sunflower.ui.theme.SunFlowerTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val Context.AppSettingsStore by dataStore("app-settings.json", AppSettingsSerializer)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val appSettings = AppSettingsStore.data.collectAsState(initial = AppSettings()).value
            val scope = rememberCoroutineScope()
            val database = Room.databaseBuilder(this, UserDatabase::class.java, "db_user").build()
            val dao = database.userDao()
            val viewModel = UserViewModel(dao)
            SunFlowerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(appSettings, viewModel) {
                        scope.launch {
                            setFirstUseFalse()
                        }
                    }
                }
            }
        }
    }
    private suspend fun setFirstUseFalse() {
        AppSettingsStore.updateData {
            it.copy(
                firstUse = false
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun App(appSettings: AppSettings, viewModel: UserViewModel, alreadyUse: () -> Unit) {
    var page = remember{ mutableStateOf(0) }
    LaunchedEffect(Unit) {
        delay(200)
        if(!appSettings.firstUse) {
            page.value = 3
        }
        else {
            page.value = 1
        }
    }
    Box(modifier = Modifier) {
        when(page.value) {
            0 -> {
                /*
                val question = questionsSource.questions[0]
                Question(question)
                 */
            }
            1 -> {
                OnBoarding(page)
            }
            2 -> {
                Profile(page , viewModel)
            }
            3 -> {
                alreadyUse()
                Home(viewModel)
            }
        }
    }
}

@Composable
fun Question(question: Question, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(question.answers[1] ) }
        Text(
            text = question.question,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
        )
        Spacer(modifier = Modifier.height(10.dp))
        question.answers.forEach {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = (it == selectedOption), onClick = { onOptionSelected(it) } )
                Text(it)
            }
        }
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End) {
            Button(onClick = { /*TODO*/ }) {
                Text("Responder")
            }
        }
    }
}


