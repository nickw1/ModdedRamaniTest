package com.example.moddedramanitest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.moddedramanitest.ui.theme.ModdedRamaniTestTheme
import com.google.gson.Gson
import com.google.gson.JsonPrimitive
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.Style
import org.ramani.compose.CameraPosition
import org.ramani.compose.Circle
import org.ramani.compose.MapLibre
import org.ramani.compose.Symbol


data class Poi(val name: String, val type: String) {
    override fun toString() = "$name ($type)"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gson = Gson()

        setContent {
            val latLng = LatLng(51.049, -0.723)
            ModdedRamaniTestTheme {
                MapLibre(modifier=Modifier.fillMaxSize(),
                    cameraPosition = CameraPosition(target= latLng, zoom=14.0),
                    styleBuilder = Style.Builder().fromUri("https://tiles.openfreemap.org/styles/bright"),
                    onMapClick = {
                        Toast.makeText(this, "Clicked on map at position $it", Toast.LENGTH_LONG).show()
                    },
                    onMapLongClick = {
                        Toast.makeText(this, "Long-clicked on map at position $it", Toast.LENGTH_LONG).show()
                    }
                ) {
                    Circle(
                        center = latLng,
                        radius = 100f,
                        borderColor = "orange",
                        borderWidth = 5f,
                        opacity = 0.3f,
                        data = gson.toJsonTree(Poi("Fernhurst", "Village")),
                        onClick = {
                            val obj = gson.fromJson(it, Poi::class.java)
                            Toast.makeText(this, "Clicked $obj", Toast.LENGTH_LONG).show()
                        },
                        onLongClick = {
                            val obj = gson.fromJson(it, Poi::class.java)
                            Toast.makeText(this, "Long-clicked $obj", Toast.LENGTH_LONG).show()
                        }
                    )
                    Symbol (
                        isDraggable = true,
                        center = LatLng(51.05, -0.73),
                        imageId = org.maplibre.android.R.drawable.maplibre_marker_icon_default,
                        size = 2f,
                        data = JsonPrimitive("Nothing in particular here"),
                        onClick = {
                            Toast.makeText(this, "Clicked annotation with data $it", Toast.LENGTH_LONG).show()
                        },
                        onLongClick = {
                            Toast.makeText(this, "Long-clicked annotation with data $it", Toast.LENGTH_LONG).show()
                        }
                    )
                }
            }
        }
    }
}

