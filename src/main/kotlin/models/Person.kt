package models

data class Person(
    var subjectId: Int,
    val verified: Boolean,
    val weight: Float,
    val location: Int?,
    val openTo: Int?
): Table{
    override val table_name="people"
    override val exist_validation_fields = mutableMapOf(
        "subjectId" to subjectId.toString(),
        //"verified" to verified.toString(),
        "weight" to weight.toString(),
        "location_id" to location.toString(),
        "openTo" to openTo.toString(),
    )

}


