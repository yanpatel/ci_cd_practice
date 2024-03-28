package com.demo.apps.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.demo.apps.ShapeType

@Composable
fun ShapePickerDialog(onShapeSelected: (ShapeType?) -> Unit, onDismissRequest: () -> Unit) {
    var selectedShape by remember { mutableStateOf<ShapeType?>(null) }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Choose a Shape", style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(16.dp))

                // Shape options in a horizontal layout
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Circle shape option
                    Box(modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(if (selectedShape == ShapeType.Circle) Color.Gray else Color.LightGray)
                        .clickable { selectedShape = ShapeType.Circle }
                    )

                    // Rectangle shape option
                    Box(modifier = Modifier
                        .size(50.dp, 30.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(if (selectedShape == ShapeType.Rectangle) Color.Gray else Color.LightGray)
                        .clickable { selectedShape = ShapeType.Rectangle }
                    )

                    // Custom shape option
                    IconButton(onClick = { selectedShape = ShapeType.Custom;
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Custom Shape")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Submit button
                Button(
                    onClick = {
                        if (selectedShape != null) {
                            onShapeSelected(selectedShape)
                            onDismissRequest()
                        }
                        // Optionally, you can add else block to show a message if no shape is selected
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedShape != null // Disable button if no shape is selected
                ) {
                    Text("Submit")
                }
            }
        }
    }
}
