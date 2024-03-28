import android.os.Build
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import java.lang.Math.cos
import java.lang.Math.sin
import kotlin.math.atan2

@Composable
fun ColorPicker(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier
) {
    var red by remember { mutableStateOf(selectedColor.red) }
    var green by remember { mutableStateOf(selectedColor.green) }
    var blue by remember { mutableStateOf(selectedColor.blue) }

    var backgroundColor by remember { mutableStateOf(selectedColor) }

    Column(modifier = modifier) {
        ColorWheel { color ->
            backgroundColor = color
            red = color.red
            green = color.green
            blue = color.blue
            onColorSelected(backgroundColor)
        }

        Slider(
            value = red,
            onValueChange = {
                red = it
                backgroundColor = Color(red, green, blue)
                onColorSelected(backgroundColor)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // ... Green and Blue sliders

        // Display selected color
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(backgroundColor)
                .padding(vertical = 8.dp)
        )
    }
}

@Composable
fun ColorWheel(onColorSelected: (Color) -> Unit) {
    val radius = 150f // Increase the radius for a larger wheel
    val gradientColors = generateColorGradient()
    var selectedColor by remember { mutableStateOf(Color.Red) }

    Canvas(modifier = Modifier
        .size((2 * radius).dp)
        .pointerInput(Unit) {
            detectTapGestures { offset ->
                val x = offset.x - radius
                val y = offset.y - radius
                val angle = atan2(y, x).toDouble()
                var hue = Math.toDegrees(angle).toFloat() % 360
                if (hue < 0) hue += 360 // Ensure hue is within 0 to 360 range
                selectedColor = Color.hsv(hue, 1f, 1f)
                onColorSelected(selectedColor)
            }
        }
    ) {
        drawCircle(
            brush = Brush.sweepGradient(gradientColors, center = Offset(radius, radius)),
            radius = radius
        )

        // Calculate the position of the indicator
        val hue = selectedColor.toHsvComponents()[0]
        val angle = Math.toRadians(hue.toDouble())
        val indicatorX = radius + radius * cos(angle).toFloat()
        val indicatorY = radius + radius * sin(angle).toFloat()

        // Draw the indicator (e.g., a small circle)
        drawCircle(
            color = selectedColor,
            radius = 10f,
            center = Offset(indicatorX, indicatorY)
        )
    }
}

private fun generateColorGradient(): List<Color> {
    return List(360) { i ->
        Color.hsv(i.toFloat(), 1f, 1f)
    }
}

private fun Color.toHsvComponents(): FloatArray {
    val hsv = FloatArray(3)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        android.graphics.Color.colorToHSV(android.graphics.Color.argb(alpha, red, green, blue), hsv)
    }
    return hsv
}