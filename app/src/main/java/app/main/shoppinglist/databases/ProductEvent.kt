package app.main.shoppinglist.databases

sealed interface ProductEvent {
    data object SaveProduct : ProductEvent
    data class SetName(val name: String) : ProductEvent
    data class SetCount(val count: String) : ProductEvent
    data class DeleteProduct(val product: Products) : ProductEvent
    data object ShowDialog : ProductEvent
    data object HideDialog : ProductEvent

}