package models

data class Skill(
    var id: Int?,
    val skill: String
): Table{
    override val table_name="skill"
    override val exist_validation_fields = mutableMapOf(
        "skill" to skill
    )

}
