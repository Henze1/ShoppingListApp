package app.main.shoppinglist.databases

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Products::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productsDao(): ProductDao
}