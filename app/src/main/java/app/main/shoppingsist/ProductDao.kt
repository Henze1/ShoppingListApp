//ProductDao

package app.main.shoppingsist

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Upsert
    suspend fun insertProduct(products: Products)

    @Query("SELECT * FROM products_database ORDER BY Name ASC")
    fun getAlphabetizedWords(): Flow<List<Products>>

    @Query("DELETE FROM products_database")
    suspend fun deleteAll()
}