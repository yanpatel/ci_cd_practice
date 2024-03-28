package com.demo.apps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.demo.apps.data.NavigationPath
import com.demo.apps.data.TextProperties
import com.demo.apps.ui.editingScreen.BoxPropertiesScreen
import com.demo.apps.ui.theme.DemoAppsTheme
import com.demo.apps.ui.theme.ShapePickerDialog
import com.demo.apps.viewModel.TextListViewModel

class MainActivity : ComponentActivity() {

    val viewModel: TextListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DemoAppsTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NavigationPath.home
                ) {
                    composable(NavigationPath.home) {
                        BlankContainer(navController)
                    }
                    composable(NavigationPath.textEditingScreen) {_  ->
                        TextEditingScreen(navController = navController,viewModel = viewModel)
                    }
                    composable(NavigationPath.boxPropertiesScreen) {_  ->
                        BoxPropertiesScreen(navController = navController,viewModel = viewModel)
                    }
                }
            }
        }
    }


    @Composable
    fun BlankContainer(navController: NavController) {
        var showDialog by remember { mutableStateOf(false) }
        var showShapeDialog by remember { mutableStateOf(false) }
        var selectedShape by remember { mutableStateOf<ShapeType?>(null) }

        Column(
            modifier = Modifier
                .fillMaxSize() // Take up the entire available space
                .wrapContentSize(Alignment.TopCenter) // Center the content inside the Box
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.95f)  // 80% of screen width
                    .fillMaxHeight(0.9f) // 80% of screen height
                    .background(Color.Gray) // Optional: Background color to visualize the container
            ) {
                viewModel.textPropertiesList.forEachIndexed { position,textProperties ->
                    textProperties.loadCompose(
                        modifier = Modifier,
                        navController = navController,
                        viewModel = viewModel,
                        position)
                }
            }
            Row {

                AssistChip(label = "Text", onClick = {
                    showDialog = true
                })

                AssistChip(label = "Box", onClick = {
                    showShapeDialog = true
                })
            }
            if (showDialog)
                AddTextPopupDialog(viewModel) {
                    showDialog = false
                }

            if (showShapeDialog) {
                ShapePickerDialog(
                    onShapeSelected = { shape ->
                        selectedShape = shape
                        showShapeDialog = false
                        navController.navigate(NavigationPath.boxPropertiesScreen)
                    },
                    onDismissRequest = { showDialog = false }
                )
            }

        }
    }

    @Composable
    fun AssistChip(label: String, onClick: () -> Unit) {
        AssistChip(
            label = { Text(text = label) },
            onClick = onClick,
            modifier = Modifier.padding(8.dp),
            leadingIcon = {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Localized description",
                    Modifier.size(AssistChipDefaults.IconSize)
                )
            }
        )
    }


    @Composable
    fun AddTextPopupDialog(viewModel: TextListViewModel, onDismissRequest: () -> Unit) {
        Dialog(onDismissRequest = onDismissRequest) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 8.dp
            ) {
                AddTextUI(viewModel, onDismissRequest)
            }
        }

    }

    @Composable
    fun AddTextUI(viewModel: TextListViewModel, onDismissRequest: () -> Unit) {
        var textInput by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                label = { Text("Enter Text") },
                modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (textInput.isNotBlank()) {
                        viewModel.addTextProperties(TextProperties(text = textInput))
                        textInput = "" // Clear the input field
                    }
                },
                modifier = Modifier.align(Alignment.End)) {
                Text("Add Text")
            }

            Button(
                onClick = {
                    onDismissRequest.invoke()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Close")
            }
        }
    }

}
