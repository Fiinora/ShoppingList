package com.example.shoppinglist


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.ui.theme.ShoppingListTheme

data class ShoppingItem(
    val id:Int,
    var name: String,
    var quantity: Int,
    var isEditting: Boolean = false){}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListApp(modifier: Modifier = Modifier){
    var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var ShowDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Center, // start di tengah kiri

//                        horizontalAlignment = Alignment.CenterHorizontally // start di tengah atas
    ) {
//                        Text("Halo")
//                        Text("Halo")
//                        Text("Halo")
        Button(onClick = {ShowDialog = true},
            modifier = Modifier.align(Alignment.CenterHorizontally
            ))
        {
            Text("Add item")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(sItems){

            }
        }

    }
    if (ShowDialog){
        AlertDialog(onDismissRequest = {ShowDialog = false}){
            Text("ello")
        }
    }
}
