package load

import models.Table
import java.sql.*
import java.util.*

object DbConnection {
    var conn: Connection? = null
    internal const val username = "torre_test"
    internal const val password = "torre_test"


    fun executeQuery(query: String): ResultSet? {
        var stmt: Statement? = null
        var result: ResultSet? = null

        try {
            stmt = conn!!.createStatement()
            stmt!!.executeQuery(query)

            if (stmt.execute(query)) {
                result = stmt.resultSet
                return result
            }

        } catch (ex: SQLException) {
            println("SQL Error")
            ex.printStackTrace()
            return null
        }
        return null
    }

    fun executeDML(query: String): Int {
        var stmt: Statement? = null
        var result: Int? = null

        try {
            stmt = conn!!.createStatement()
            result = stmt!!.executeUpdate(query)

            stmt.execute(query)
            return result

        } catch (ex: SQLException) {
            println("SQL Error")
            ex.printStackTrace()
            return 0
        }
    }

    fun getConnection() {
        val props = Properties()
        props["user"] = username
        props["password"] = password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance()
            val connurl = "jdbc:mysql://localhost:3306/torre_test"
            conn = DriverManager.getConnection(connurl, props)
        } catch (ex: SQLException) {
            println("SQL Error")
            ex.printStackTrace()
        } catch (ex: Exception) {
            println("Unkown error")
            ex.printStackTrace()
        }
    }

    fun insert_or_ignore(obj: Table) {
        val keys = obj.exist_validation_fields.keys.joinToString(",")
        val values = obj.exist_validation_fields.values.map { value -> "'${value}'" }.joinToString(",")
        val key_vals = obj.exist_validation_fields.map { (key, value) -> "$key = '$value'" }.joinToString(" AND ")

        var query = "INSERT INTO torre_test.${obj.table_name}"
        query = "$query ( $keys ) SELECT $values FROM dual WHERE NOT EXISTS "
        query = "$query ( SELECT $keys FROM torre_test.${obj.table_name} WHERE $key_vals );"
        executeDML(query)

    }

    fun getObjId(obj: Table, id_field: String): Int?{
        val key_vals = obj.exist_validation_fields.map { (key, value) -> "$key = '$value'" }.joinToString(" AND ")
        val query = "SELECT $id_field FROM torre_test.${obj.table_name} WHERE $key_vals"
        val rs = executeQuery(query) ?: return null
        while (rs.next()){
            return rs.getInt(id_field)
        }
        return null
    }
}