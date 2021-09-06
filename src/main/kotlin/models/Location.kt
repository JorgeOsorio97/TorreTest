package models

data class Location(
    var id: Int?,
    val name: String,
    val country_id: Int,
    val city_id: Int?,
): Table{
    override val table_name="location"
    override val exist_validation_fields = mutableMapOf(
        "country_id" to country_id.toString(),
        "city_id" to city_id.toString()
    )

}
// TODO: Future add
//data class OpenTo(
//
//)