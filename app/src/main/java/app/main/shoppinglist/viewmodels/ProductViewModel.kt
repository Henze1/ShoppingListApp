package app.main.shoppinglist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.main.shoppinglist.databases.ProductDao
import app.main.shoppinglist.databases.Products
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductDao): ViewModel() {
    val allProducts = productRepository.getAllProducts()

    fun upsert(product: Products) = viewModelScope.launch {
        productRepository.upsertProduct(product)
    }

    fun delete(product: Products) = viewModelScope.launch {
        productRepository.deleteProduct(product)
    }
}