package com.demo.apps.ui.editingScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.demo.apps.viewModel.TextListViewModel

@ExperimentalMaterial3Api
@Composable
fun BoxPropertiesScreen(navController: NavController, viewModel: TextListViewModel) {
    var size by remember { mutableStateOf(100.dp) }
    var color by remember { mutableStateOf(Color.Red) }
    var shapeType by remember { mutableStateOf("Rectangle") }
    var padding by remember { mutableStateOf(0.dp) }
    var contentText by remember { mutableStateOf("Content") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Box Properties") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                 }
            )
        }
    ) { paddingValues ->
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PropertyControls(
                    size = size,
                    color = color,
                    shapeType = shapeType,
                    padding = padding,
                    contentText = contentText,
                    onSizeChange = { size = it.dp },
                    onColorChange = { color = if (color == Color.Red) Color.Blue else Color.Red },
                    onShapeChange = { shapeType = it },
                    onPaddingChange = { padding = it.dp },
                    onContentChange = { contentText = it }
                )

                BoxPreview(
                    size = size,
                    color = color,
                    shapeType = shapeType,
                    padding = padding,
                    contentText = contentText
                )

                Button(
                    onClick = {
                        // Implement the logic to apply the changes
//                        viewModel.applyChanges(size, color, shapeType, padding, contentText)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Apply Changes")
                }
            }
        }
    }
}

// The PropertyControls and BoxPreview composable functions remain the same

@Composable
fun PropertyControls(
    size: Dp,
    color: Color,
    shapeType: String,
    padding: Dp,
    contentText: String,
    onSizeChange: (Float) -> Unit,
    onColorChange: () -> Unit,
    onShapeChange: (String) -> Unit,
    onPaddingChange: (Float) -> Unit,
    onContentChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Size: ${size.value.toInt()}")
        Slider(value = size.value, onValueChange = onSizeChange, valueRange = 50f..200f)

        Button(onClick = onColorChange) {
            Text("Change Color")
        }

        Text("Padding: ${padding.value.toInt()}")
        Slider(value = padding.value, onValueChange = onPaddingChange, valueRange = 0f..50f)

        OutlinedTextField(
            value = contentText,
            onValueChange = onContentChange,
            label = { Text("Content Text") }
        )

        // Shape selection
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Shape:")
            ShapeSelector(shape = "Rectangle", isSelected = shapeType == "Rectangle") {
                onShapeChange("Rectangle")
            }
            ShapeSelector(shape = "Circle", isSelected = shapeType == "Circle") {
                onShapeChange("Circle")
            }
        }

    }
}

@Composable
fun ShapeSelector(shape: String, isSelected: Boolean, onClick: () -> Unit) {
    val shapeSize = 24.dp
    val shapeColor = if (isSelected) Color.Blue else Color.Gray
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(shapeSize)
            .background(shapeColor, if (shape == "Circle") CircleShape else RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)){}
}

@Composable
fun BoxPreview(
    size: Dp,
    color: Color,
    shapeType: String,
    padding: Dp,
    contentText: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
            .background(color, if (shapeType == "Circle") CircleShape else RoundedCornerShape(4.dp))
            .padding(padding)
    ) {
        Text(contentText)
    }
}
