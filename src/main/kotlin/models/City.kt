package models

data class City(
    var id: Int?,
    val city: String
): Table{
    override val table_name="city"
    override val exist_validation_fields = mutableMapOf(
        "city" to city
    )

}

