package app.main.shoppinglist.databases

class ProductRepository(private val productDao: ProductDao) {

    val allProducts: List<Products> = productDao.getAllProducts()

    suspend fun upsert(product: Products) {
        productDao.upsertProduct(product)
    }

    suspend fun deleteProduct(product: Products) {
        productDao.deleteProduct(product)
    }
}