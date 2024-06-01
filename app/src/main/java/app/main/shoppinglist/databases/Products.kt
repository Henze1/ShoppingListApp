package app.main.shoppinglist.databases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Products(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo(name = "name") var name : String,
    @ColumnInfo(name = "count") var count : Int,
    var isEditing : Boolean = false
)