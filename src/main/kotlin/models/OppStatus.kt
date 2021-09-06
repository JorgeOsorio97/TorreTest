package models

data class OppStatus(
    var id: Int?,
    val status: String
): Table{
    override val table_name="status"
    override val exist_validation_fields = mutableMapOf(
        "status" to status
    )

}

