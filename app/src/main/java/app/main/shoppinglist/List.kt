package app.main.shoppinglist

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import app.main.shoppinglist.databases.AppDatabase
import app.main.shoppinglist.databases.Products
import app.main.shoppinglist.langsupport.Armenian
import app.main.shoppinglist.langsupport.English
import app.main.shoppinglist.langsupport.Language
import app.main.shoppinglist.langsupport.Russian
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun ShoppingList(
    database: AppDatabase
) {

    var setItems by remember{ mutableStateOf(listOf<Products>()) }

    var showDialog by remember { mutableStateOf(false)}
    var itemName by remember { mutableStateOf("")}
    var itemQuantity by remember { mutableStateOf("")}

    var currLanguage: Language by remember { mutableStateOf(English()) }
    var showFlagsDialog by remember { mutableStateOf(false)}

    val langList = listOf(English(), Armenian(), Russian())


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
                    ShoppingListEditor(
                        lang = currLanguage,
                        item = item,
                        onEditComplete = {
                            editedName, editedQuantity ->
                            setItems = setItems.map { it.copy(isEditing = false) }
                            val editedItem = setItems.find { it.id == item.id }
                            editedItem?.let {
                                it.name = editedName
                                it.count = editedQuantity
                                GlobalScope.launch {
                                    database.productsDao().upsertProduct(product = it)
                                }
                            }
                        }
                    )
                } else {
                    ShoppingListItem(
                        lang = currLanguage,
                        item = item,
                        onEditClick = {
                            setItems = setItems.
                            map { it.copy(isEditing = it.id == item.id) }
                        },
                        onDeleteClick = {
                            setItems -= item
                            GlobalScope.launch {
                                database.productsDao().deleteProduct(product = item)
                            }
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
                                 val newItem = Products(
                                     id = setItems.size + 1,
                                     name = itemName,
                                     count = itemQuantity.toInt(),
                                     isEditing = false
                                 )
                                 setItems = setItems + newItem
                                 showDialog = false
                                 itemName = ""
                                 itemQuantity = ""
                             }
                         }) {
                         Text(text = currLanguage.add)
                     }
                     Button(
                         onClick = { showDialog = false }) {
                         Text(text = currLanguage.cancel)
                     }
                 }
             },
             title = { Text(text = currLanguage.add)},
             text = {
                 Column {
                     OutlinedTextField(
                         value = itemName,
                         onValueChange = { itemName = it },
                         singleLine = true,
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding(6.dp),
                         label = { Text(text = currLanguage.name) }
                     )
                     OutlinedTextField(
                         value = itemQuantity,
                         onValueChange = { itemQuantity = if (it.isDigitsOnly()) it else 0.toString() },
                         singleLine = true,
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding(6.dp),
                         label = { Text(text = currLanguage.count) }
                     )
                 }
             }
         )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            containerColor = Color.Gray,
            contentColor = Color.White,
            shape = RoundedCornerShape(10),
            onClick = {
                showFlagsDialog = true
            },
            modifier = Modifier
                .padding(16.dp)
                .width(50.dp)
                .height(27.dp)
            ,
            content = {
                Image(
                    modifier = Modifier
                        .size(50.dp),
                    painter = painterResource(currLanguage.flag),
                    contentDescription = currLanguage.language,
                )
            }
        )
    }

    if (showFlagsDialog) {
        AlertDialog(
            onDismissRequest = { showFlagsDialog = false },
            confirmButton = {
                LazyColumn {
                    items(
                        key = { it.langName },
                        items = langList
                    ) { lang ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                painter = painterResource(id = lang.flag),
                                contentDescription = lang.langName,
                                modifier = Modifier
                                    .size(50.dp)
                            )

                            Button(
                                onClick = {
                                    currLanguage = lang
                                    showFlagsDialog = false
                                }) {
                                Text(text = lang.langName)
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun ShoppingListEditor(
    lang: Language,
    item: Products,
    onEditComplete: (String, Int) -> Unit
) {
    var editedName by remember { mutableStateOf(item.name)}
    var editedQuantity by remember { mutableStateOf(item.count.toString())}
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
            Text(text = lang.save)
        }
    }
}

@Composable
fun ShoppingListItem(
    lang: Language,
    item: Products,
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
        Text(text = "${lang.count}: ${item.count}", modifier = Modifier.padding(6.dp))
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