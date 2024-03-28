package com.demo.apps

import ColorPicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.apps.data.TextProperties
// Import your custom ColorPicker composable if it's in a different package

// Additional imports for specific UI elements or functionalities
// For example, if you have a custom FontFamily picker, you would import that here

import androidx.navigation.NavController
import com.demo.apps.viewModel.TextListViewModel


@Composable
fun TextEditingScreen(navController: NavController, viewModel: TextListViewModel) {
    val textPropertiesPair = viewModel.textPropertiesModel.value
    val textProperties = textPropertiesPair?.second ?: return
    val position = textPropertiesPair.first

    var text by remember { mutableStateOf(textProperties.text) }
    var fontSize by remember { mutableStateOf(textProperties.fontSize) }
    var color by remember { mutableStateOf(textProperties.color) }
    var fontWeight by remember { mutableStateOf(textProperties.fontWeight) }
    var fontFamily by remember { mutableStateOf(textProperties.fontFamily) }
    var letterSpacing by remember { mutableStateOf(textProperties.letterSpacing) }
    var lineHeight by remember { mutableStateOf(textProperties.lineHeight) }
    var textDecoration by remember { mutableStateOf(textProperties.textDecoration) }
    var textAlign by remember { mutableStateOf(textProperties.textAlign) }
    var overflow by remember { mutableStateOf(textProperties.overflow) }
    var maxLines by remember { mutableStateOf(textProperties.maxLines) }
    var softWrap by remember { mutableStateOf(textProperties.softWrap) }

    val fontWeights = listOf(
        FontWeight.Thin,
        FontWeight.ExtraLight,
        FontWeight.Light,
        FontWeight.Normal,
        FontWeight.Medium,
        FontWeight.SemiBold,
        FontWeight.Bold,
        FontWeight.ExtraBold,
        FontWeight.Black
    )
    var fontWeightExpanded by remember { mutableStateOf(false) }


    val fontFamilies = listOf(
        FontFamily.Default,
        FontFamily.Cursive,
        FontFamily.Serif,
        FontFamily.SansSerif,
        FontFamily.Monospace
    )
    var expanded by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
        // Edit Text
        OutlinedTextField(
            value = text,
            onValueChange = { newText -> text = newText },
            label = { Text("Enter text") },
            textStyle = TextStyle(color,fontFamily = fontFamily, fontWeight = fontWeight),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp))

        // Font Size
        OutlinedTextField(
            value = fontSize.value.toString(),
            onValueChange = { newSize ->
                fontSize = if (newSize.isBlank()) 16.sp else newSize.toFloat().sp
            },
            label = { Text("Font Size") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp))
        Box {
            // Font Family Dropdown
            OutlinedTextField(
                value = fontFamily.toString(),
                onValueChange = {},
                label = { Text("Font Family") },
                readOnly = true,
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .clickable { expanded = true }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.clickable {
                    expanded = !expanded
                }
            ) {
                fontFamilies.forEach { fontFamilyItem ->
                    DropdownMenuItem(
                        { Text(fontFamilyItem.toString()) },
                        onClick = {
                            fontFamily = fontFamilyItem
                            expanded = false
                        })
                }
            }
        }

        //Font weight
        Box {
            // Font Weight Dropdown
            OutlinedTextField(
                value = fontWeight.toString(),
                onValueChange = {},
                label = { Text("Font Weight") },
                readOnly = true,
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .clickable { fontWeightExpanded = true }
            )

            DropdownMenu(
                expanded = fontWeightExpanded,
                onDismissRequest = { fontWeightExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                fontWeights.forEach { fontWeightItem ->
                    DropdownMenuItem(
                        {Text(fontWeightItem.toString())},
                        onClick = {
                            fontWeight = fontWeightItem
                            fontWeightExpanded = false
                        }
                    )
                }
            }
        }

        // Color Picker
        // Assuming ColorPicker is a custom composable function you have defined to pick colors
        ColorPicker(
            selectedColor = color,
            onColorSelected = { selectedColor -> color = selectedColor },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp))

        // Additional text properties (fontWeight, fontFamily, etc.)
        // Add UI components for editing letterSpacing, lineHeight, textDecoration, textAlign, overflow, maxLines, softWrap as needed

        // Button to apply changes
        Button(
            onClick = {
                viewModel.textPropertiesList[position] = TextProperties(
                    text = text,
                    fontSize = fontSize,
                    color = color,
                    fontWeight = fontWeight,
                    fontFamily = fontFamily,
                    letterSpacing = letterSpacing,
                    lineHeight = lineHeight,
                    textDecoration = textDecoration,
                    textAlign = textAlign,
                    overflow = overflow,
                    maxLines = maxLines,
                    softWrap = softWrap
                )
                viewModel.textPropertiesModel.value = Pair(
                    position, TextProperties(
                        text = text,
                        fontSize = fontSize,
                        color = color,
                        fontWeight = fontWeight,
                        fontFamily = fontFamily,
                        letterSpacing = letterSpacing,
                        lineHeight = lineHeight,
                        textDecoration = textDecoration,
                        textAlign = textAlign,
                        overflow = overflow,
                        maxLines = maxLines,
                        softWrap = softWrap
                    )
                )
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Apply Changes")
        }
    }
}
