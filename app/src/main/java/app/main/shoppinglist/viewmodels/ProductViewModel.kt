package app.main.shoppinglist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.main.shoppinglist.databases.ProductDao
import app.main.shoppinglist.databases.ProductEvent
import app.main.shoppinglist.databases.ProductState
import app.main.shoppinglist.databases.Products
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModel(
    private val productDao: ProductDao
): ViewModel() {
    private val _sortedProducts = MutableStateFlow(0)
    private val _products = _sortedProducts
        .flatMapLatest { sortType ->
            when (sortType) {
                0 -> productDao.getProductsSortedByName()
                else -> productDao.getAllProducts()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(ProductState())
    val state = combine(_state, _products) { state, products ->
        state.copy(
            products = products,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductState())

    fun onEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.DeleteProduct -> {
                viewModelScope.launch {
                    productDao.deleteProduct(event.product)
                }
            }

            ProductEvent.SaveProduct -> {
                val name = state.value.name
                val count = state.value.count
                if (name.isBlank() || count <= 0) {
                    return
                }

                val product = Products(
                    name = name,
                    count = count
                )
                viewModelScope.launch {
                    productDao.upsertProduct(product)
                }
                _state.update { it.copy(
                    name = "",
                    count = 0
                ) }
            }
            is ProductEvent.SetCount -> {
                _state.update { it.copy(
                    count = event.count
                ) }
            }
            is ProductEvent.SetName -> {
                _state.update { it.copy(
                    name = event.name
                ) }
            }
            ProductEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingProduct = true
                ) }
            }
            ProductEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingProduct = false
                ) }
            }
        }
    }
}