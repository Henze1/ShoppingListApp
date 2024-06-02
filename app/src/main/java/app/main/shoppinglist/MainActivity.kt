package app.main.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import app.main.shoppinglist.databases.AppDatabase
import app.main.shoppinglist.databases.ProductRepository
import app.main.shoppinglist.ui.theme.ShoppingListAppTheme
import app.main.shoppinglist.viewmodels.ProductViewModel
import app.main.shoppinglist.viewmodels.ProductsViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            ShoppingListAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    val database = remember { AppDatabase.getDatabase(context) }
                    val repository = remember { ProductRepository(database.productsDao()) }
                    val viewModel: ProductViewModel = viewModel(factory = ProductsViewModelFactory(repository))

                    ShoppingList(viewModel)
                }
            }
        }
    }
}