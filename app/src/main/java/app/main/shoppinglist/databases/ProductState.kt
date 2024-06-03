package app.main.shoppinglist.databases

data class ProductState(
    val products: List<Products> = emptyList(),
    val name: String = "",
    val count: Int = 0,
    val isAddingProduct: Boolean = false
)
