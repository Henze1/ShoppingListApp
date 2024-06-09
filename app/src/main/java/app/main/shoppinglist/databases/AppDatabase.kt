package app.main.shoppinglist.databases

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Products::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productsDao(): ProductDao
}