package app.main.shoppingsist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

class ProductsViewModel(private val repository: ProductRepository) : ViewModel() {

    val allProducts : Flow<List<Products>> = repository.allProducts
}
