package app.main.shoppinglist.databases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Upsert
    suspend fun upsertProduct(product: Products)

    @Delete
    suspend fun deleteProduct(product: Products)

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Products>>

    @Query("SELECT * FROM products ORDER BY name ASC")
    fun getProductsSortedByName(): Flow<List<Products>>

}