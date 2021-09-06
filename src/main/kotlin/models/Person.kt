package models

data class Person(
    val subjectId: Int,
    val verified: Boolean,
    val weight: Float,
    val location: Int?,
    val openTo: Int?
)


