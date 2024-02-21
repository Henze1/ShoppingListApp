//Products

package app.main.shoppingsist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products_database")
data class Products(
    @ColumnInfo(name = "Name") var name : String,
    @ColumnInfo(name = "Count") var count : Int,
    var isEditing : Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0
)