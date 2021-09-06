package models

import java.util.*

data class Opportunity(
    var id: Int?,
    val objective: String,
    val remote: Boolean,
    val deadline: Date,
    val created: Date,
    val type_id: Int,
    val status_id: Int
): Table{
    override val table_name="opps"
    override val exist_validation_fields = mutableMapOf(
        "id" to id.toString()
    )

}
