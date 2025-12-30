package com.example.shoppinglist


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.ui.theme.ShoppingListTheme

import androidx.compose.ui.text.TextStyle
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
    var itemName by remember { mutableStateOf("") }
    var itemQty by remember { mutableStateOf("") }
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
        Button(onClick = {ShowDialog = true},//trigger muncul alertdialog
            border = BorderStroke(2.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent // button ngga bisa bawa parameter Color.black untuk colorsnya
            ),
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
                item ->
                if (item.isEditting){
                    ShoppingItemEditor(item = item, onEditComplete = {
                        editedName, editetQty ->
                        sItems = sItems.map{it.copy(isEditting = false)}
                        val editedItem = sItems.find { it.id == item.id }// nyari, mana bagian atau id yang mau diubah
                        editedItem?.let {
                            it.name = editedName
                            it.quantity = editetQty
                        }
                    })
                }else{
                    ShoppingListItem(item = item , onEditClick = {
                        //ngeloop list di sitems,
                        sItems = sItems.map { it.copy(isEditting = it.id == item.id) }
                    },
                        onDeleteClick = {
                            sItems = sItems - item
                        })



                }

            }
        }

    }
    if (ShowDialog){//jika show dialog true, tampilkan alert

        AlertDialog(onDismissRequest = {ShowDialog = false},
            containerColor = Color.White,
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                    Button(onClick = {
                        if (itemName.isNotBlank()){
                            val newItem = ShoppingItem(id=sItems.size+1,
                                name = itemName,
                                quantity = itemQty.toInt())
                            sItems = sItems + newItem
                            ShowDialog = false
                            itemName = ""
                        }
                    },
                    border = BorderStroke(2.dp, Color.Black),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent // button ngga bisa bawa parameter Color.black untuk colorsnya
                        )) {
                        Text("Add")
                    }
                    Button(onClick = {ShowDialog = false},
                        border = BorderStroke(2.dp, Color.Black),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent // button ngga bisa bawa parameter Color.black untuk colorsnya
                        )
                    ) {
                        Text("Cancel")
                    }
                }
            },
            title = {Text("Add Shopping Item", color = Color.Black)},
            text = {
                Column {
                    OutlinedTextField(value = itemName, onValueChange = {itemName = it},
                        singleLine = true,
                        shape = RoundedCornerShape(20),
                        textStyle = TextStyle(color = Color.Black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp))


                    OutlinedTextField(value = itemQty, onValueChange = {itemQty = it},
                        singleLine = true,
                        shape = RoundedCornerShape(20),
                        textStyle = TextStyle(color = Color.Black),
                        modifier = Modifier.fillMaxWidth().padding(8.dp))
                }
            }
            )

        }
    }


@Composable
fun ShoppingItemEditor(
    item: ShoppingItem,
    onEditComplete: (String, Int) -> Unit
){
    var editedName by remember { mutableStateOf(item.name) }
    var editedQty by remember { mutableStateOf(item.quantity.toString()) }
    var isEditting by remember { mutableStateOf(item.isEditting) }

    Row(
        modifier = Modifier.fillMaxWidth().background(Color.Gray).padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column {
            OutlinedTextField(
                value = editedName,
                onValueChange = {editedName = it},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = editedQty,
                onValueChange = {editedQty = it},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(onClick = {
            isEditting = false
            onEditComplete(editedName, editedQty.toIntOrNull() ?: 1)
        }) {
            Text("Save")
        }
    }
}

@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(width = 2.dp, Color.Black,),
                shape = RoundedCornerShape(20)
            )
            .padding(20.dp), //konten dengan border
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Text(item.name, modifier = Modifier.padding(8.dp)) // nampilin data name yang habis diinput
        Text("Qty : ${item.quantity}", modifier = Modifier.padding(8.dp))
        Row (
            modifier = Modifier.padding(8.dp)
        ) {
            //Button edit
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"

                )
            }
            //button delete
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"

                )
            }
        }

    }
}
