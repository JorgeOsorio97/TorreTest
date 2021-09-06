package models

import java.util.*

data class Opportunity(
    val id: Int?,
    val objective: String,
    val remote: Boolean,
    val deadline: Date,
    val created: Date,
    val type_id: Int,
    val status_id: Int
)
