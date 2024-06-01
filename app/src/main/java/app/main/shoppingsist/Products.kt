package app.main.shoppingsist

data class Products(
    var name : String,
    var count : Int,
    var isEditing : Boolean = false,
    val id : Int = 0
)