package com.erenalparslan.jetpackcomposelearn


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erenalparslan.jetpackcomposelearn.ui.theme.JetpackComposeLearnTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeLearnTheme {
                BottomNavigationView()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationView() {
    var selectedItem by rememberSaveable { mutableStateOf("Home") }

    val navController = rememberNavController()

    Scaffold(
        bottomBar = { NavigationBar(selectedItem, navController) }
    ) {
        Box() {
            NavHost(navController, startDestination = "home") {
                composable("home") { HomeScreen() }
                composable("search") { SearchScreen() }
            }
        }
    }
}

@Composable
fun NavigationBar(selectedItem: String, navController: NavController) {
    NavigationBar(

        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedItem == HOME_ROUTE,
            onClick = { navController.navigate(HOME_ROUTE) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            label = { Text("Search") },
            selected = selectedItem == SEARCH_ROUTE,
            onClick = { navController.navigate(SEARCH_ROUTE) }
        )
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = HOME_ROUTE) {
        composable(HOME_ROUTE) { HomeScreen() }
        composable(SEARCH_ROUTE) { SearchScreen() }
    }
}

@Composable
fun HomeScreen() {
    LoginScreen(onLoginClicked = { _, _ -> })
}

@Composable
fun SearchScreen() {
    Greeting()
}

@Composable
fun LoginScreen(onLoginClicked: (String, String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Kullanıcı Girişi", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(26.dp))
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Kullanıcı Adı") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Şifre") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onLoginClicked(username, password) }) {
                Text("Giriş Yap")
            }
        }
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Greeting() {
    var textState by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Card(
                modifier = Modifier.padding(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Gray,
                )
            ) {
                Column(
                    Modifier
                        .padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    TextField(
                        value = textState,
                        onValueChange = { textState = it },
                        modifier = Modifier // TextField'ın arka plan rengi
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(46.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        label = { Text("Text") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar(textState)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(Color.Yellow)


                    ) {
                        Text(text = "Show", color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    JetpackComposeLearnTheme {
        BottomNavigationView()
    }
}

// Constants for route names
const val HOME_ROUTE = "home"
const val SEARCH_ROUTE = "search"
