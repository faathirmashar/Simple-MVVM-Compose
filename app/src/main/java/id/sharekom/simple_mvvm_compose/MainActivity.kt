package id.sharekom.simple_mvvm_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import id.sharekom.simple_mvvm_compose.ui.theme.SimpleMVVMComposeTheme
import id.sharekom.simple_mvvm_compose.viewmodel.DummyDataViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContent {
            SimpleMVVMComposeTheme {
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
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(selected = true, onClick = {
                                snackbar()
                            })
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Box(modifier = Modifier.height(240.dp).fillMaxWidth()) {
                                Image(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = rememberImagePainter(
                                        data = "https://images.unsplash.com/photo-1644982648774-83312909bbef?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"
                                    ),
                                    contentDescription = stringResource(id = R.string.image_dummy),
                                    contentScale = ContentScale.FillWidth
                                )
                            }
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


