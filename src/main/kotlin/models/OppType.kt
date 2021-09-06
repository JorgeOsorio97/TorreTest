package models

import java.io.StringBufferInputStream

data class OppType(
    var id: Int?,
    val name: String
): Table{
    override val table_name="type"
    override val exist_validation_fields = mutableMapOf(
        "name" to name
    )

}
