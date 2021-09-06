package models


data class Country(
    var id: Int?,
    val country: String
): Table{
    override val table_name="country"
    override val exist_validation_fields = mutableMapOf(
        "country" to country
    )

}