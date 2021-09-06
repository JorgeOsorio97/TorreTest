package models

interface Table {
    val table_name: String
    val exist_validation_fields: MutableMap<String, String>
}