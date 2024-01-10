package app.main.shoppingsist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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

data class Items(
    val id : Int,
    var name : String,
    var quantity : Int,
    var isEditing : Boolean)

@Composable
fun ShoppingList() {
    var setItems by remember{ mutableStateOf(listOf<Items>()) }
    var showDialog by remember { mutableStateOf(false)}
    var itemName by remember { mutableStateOf("")}
    var itemQuantity by remember { mutableStateOf("")}

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
           Text(text = "+")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
        ) {
            items(setItems){
                item ->
                if (item.isEditing) {
                    ShoppingListEditor(item = item, onEditComplete = {
                        editedName, editedQuantity ->
                        setItems = setItems.map { it.copy(isEditing = false) }
                        val editedItem = setItems.find { it.id == item.id }
                        editedItem?.let {
                            it.name = editedName
                            it.quantity = editedQuantity
                        }
                    })
                } else {
                    ShoppingListItem(
                        item = item,
                        onEditClick = {
                            setItems = setItems.
                            map { it.copy(isEditing = it.id == item.id) }},
                        onDeleteClick = {
                            setItems -= item
                        }
                    )
                }
            }
        }
    }

    if (showDialog) {
         AlertDialog(
             onDismissRequest = { showDialog = false },
             confirmButton = {
                 Row (modifier = Modifier
                     .fillMaxWidth()
                     .padding(6.dp), 
                     horizontalArrangement = Arrangement.SpaceBetween){
                     Button(
                         onClick = {
                             if (itemName.isNotBlank()) {
                                 val newItem = Items(
                                     id = setItems.size + 1,
                                     name = itemName,
                                     quantity = itemQuantity.toInt(),
                                     isEditing = false
                                 )
                                 setItems = setItems + newItem
                                 showDialog = false
                                 itemName = ""
                                 itemQuantity = ""
                             }
                         }) {
                         Text(text = "Ավելացնել")
                     }
                     Button(
                         onClick = { showDialog = false }) {
                         Text(text = "Չեղարկել")
                     }
                 }
             },
             title = { Text(text = "Ավելացնել")},
             text = {
                 Column {
                     OutlinedTextField(
                         value = itemName,
                         onValueChange = { itemName = it },
                         singleLine = true,
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding(6.dp),
                         label = { Text(text = "Անուն") }
                     )
                     OutlinedTextField(
                         value = itemQuantity,
                         onValueChange = { itemQuantity = it },
                         singleLine = true,
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding(6.dp),
                         label = { Text(text = "Քանակ") }
                     )
                 }
             }
         )
    }
}

@Composable
fun ShoppingListEditor(item: Items, onEditComplete: (String, Int) -> Unit) {
    var editedName by remember { mutableStateOf(item.name)}
    var editedQuantity by remember { mutableStateOf(item.quantity.toString())}
    var isEditing by remember { mutableStateOf(item.isEditing)}

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .padding(6.dp),
        horizontalArrangement = Arrangement.SpaceEvenly)
    {
        Column {
            BasicTextField(
                value = editedName,
                onValueChange = {editedName = it},
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(6.dp)
            )
            BasicTextField(
                value = editedQuantity,
                onValueChange = {editedQuantity = it},
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(6.dp)
            )
        }
        
        Button(
            onClick = {
                isEditing = false
                onEditComplete(editedName, editedQuantity.toIntOrNull() ?: 1)
        }) {
            Text(text = "Պահպանել")
        }
    }
}

@Composable
fun ShoppingListItem(
    item: Items,
    onEditClick: () -> Unit,
    onDeleteClick : () -> Unit
    ) {
    Row (
        modifier = Modifier
            .padding(6.dp)
            .fillMaxSize()
            .border(
                border = BorderStroke(2.dp, Color(0xFF686a6c)),
                shape = RoundedCornerShape(25)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(text = item.name, modifier = Modifier.padding(6.dp))
        Text(text = "Քանակ: ${item.quantity}", modifier = Modifier.padding(6.dp))
        Row(modifier = Modifier.padding(6.dp)) {
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
    }

}