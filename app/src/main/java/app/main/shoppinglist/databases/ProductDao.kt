package app.main.shoppinglist.databases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ProductDao {
    @Upsert
    suspend fun upsertProduct(product: Products)

    @Query("SELECT * FROM products")
    fun getAllProducts(): List<Products>

    @Delete
    suspend fun deleteProduct(product: Products)
}