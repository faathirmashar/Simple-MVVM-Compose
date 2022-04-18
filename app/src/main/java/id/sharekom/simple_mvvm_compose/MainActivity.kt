package id.sharekom.simple_mvvm_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.sharekom.simple_mvvm_compose.ui.theme.GithubUserAppComposeTheme
import id.sharekom.simple_mvvm_compose.viewmodel.DummyDataViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubUserAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val dummyDataViewModel: DummyDataViewModel = viewModel()
    val dummyData = dummyDataViewModel.getDummyData().observeAsState()

    Scaffold(
        modifier = Modifier,
        scaffoldState = scaffoldState // attaching `scaffoldState` to the `Scaffold`
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            dummyData.value?.forEach { dummyDataItem ->
                val snackbar: () -> Unit = {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = dummyDataItem.title,
                            actionLabel = "Ok"
                        )
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(selected = true, onClick = {
                                snackbar()
                            })
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = dummyDataItem.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Text(text = dummyDataItem.body, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }


}


