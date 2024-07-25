package com.github.smalljooj.sunflowerapp.ui.commonComposables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.github.smalljooj.sunflowerapp.R
import com.github.smalljooj.sunflowerapp.data.types.model.Question

@Composable
fun QuestionDialog(
    question: Question,
    send: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        title = {
            Text(text = question.question)
        },
        onDismissRequest = { },
        confirmButton = {
            TextButton(
                onClick = {
                    send(false)
                }
            ) {
                Text(stringResource(id = R.string.no))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    send(true)
                }
            ) {
                Text(stringResource(id = R.string.yes))
            }
        }
    )
}
