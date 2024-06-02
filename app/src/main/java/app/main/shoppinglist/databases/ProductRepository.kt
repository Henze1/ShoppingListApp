package app.main.shoppinglist.databases

class ProductRepository(private val productDao: ProductDao) : ProductDao {
    override suspend fun upsertProduct(product: Products) {
        productDao.upsertProduct(product)
    }

    override fun getAllProducts(): List<Products> {
        val allProducts: List<Products> = productDao.getAllProducts()

        return allProducts
    }

    override suspend fun deleteProduct(product: Products) {
        productDao.deleteProduct(product)
    }
}