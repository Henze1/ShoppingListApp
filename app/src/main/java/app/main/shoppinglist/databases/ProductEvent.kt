package app.main.shoppinglist.databases

sealed interface ProductEvent {
    data object SaveProduct : ProductEvent
    data class SetName(val name: String) : ProductEvent
    data class SetCount(val count: Int) : ProductEvent
    data class DeleteProduct(val product: Products) : ProductEvent
}