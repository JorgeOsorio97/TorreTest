package load

import models.Table
import java.lang.System.getenv
import java.sql.*
import java.util.*

object DbConnection {
    var conn: Connection? = null
    internal val username = getenv("DATABASE_USER") ?: "torre_test"
    internal val password = getenv("DATABASE_PASSWORD") ?: "torre_test"
    internal val connurl = getenv("DATABASE_URL") ?: "jdbc:mysql://localhost:3306/torre_test"


    fun executeQuery(query: String, params: List<String>): ResultSet? {
        var stmt: Statement? = null
        var result: ResultSet? = null

        try {
            stmt = conn!!.prepareStatement(query)
            for(i in params.indices){
                stmt.setString(i+1, params[i])
            }
            stmt!!.executeQuery()

            if (stmt.execute()) {
                result = stmt.resultSet
                return result
            }

        } catch (ex: SQLException) {
            println("SQL Error")
            println(query)
            println(params)
            ex.printStackTrace()
            throw ex
        }
        return null
    }

    fun executeDML(query: String, params: List<String>): Int {
        var stmt: PreparedStatement? = null
        var result: Int? = null

        try {
            stmt = conn!!.prepareStatement(query)
            for(i in params.indices){
                stmt.setString(i+1, params[i])
            }
            result = stmt!!.executeUpdate()
            // stmt.execute(query)
            return result

        } catch (ex: SQLException) {
            println("SQL Error")
            println(query)
            println(params)
            ex.printStackTrace()
            throw ex
        }
    }

    fun getConnection() {
        val props = Properties()
        props["user"] = username
        props["password"] = password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance()
            conn = DriverManager.getConnection(connurl, props)
        } catch (ex: SQLException) {
            println("SQL Error")
            ex.printStackTrace()
            throw ex
        } catch (ex: Exception) {
            println("Unkown error")
            ex.printStackTrace()
            throw ex
        }
    }

    fun insert_or_ignore(obj: Table) {
        val exits_validation_fields = obj.exist_validation_fields.mapValues{
                (_, value) -> if (value!=null && value!="null") value else "NULL"
        }
        val keys = exits_validation_fields.keys.joinToString(",")
        val values = exits_validation_fields.values
            .map { value -> if (value=="NULL") "NULL" else "?" }
            .joinToString(",")
        val key_vals = exits_validation_fields
            .map { (key, value) -> if (value=="NULL") "$key = NULL" else "$key = ?"}
            .joinToString(" AND ")

        var query = "INSERT INTO torre_test.${obj.table_name}"
        query = "$query ( $keys ) SELECT $values FROM dual WHERE NOT EXISTS "
        query = "$query ( SELECT $keys FROM torre_test.${obj.table_name} WHERE $key_vals );"
        var params: MutableList<String> = mutableListOf()
        val non_null_values = exits_validation_fields.values.filter{it!="NULL"}
        for (value in non_null_values) params.add(value)
        for (value in non_null_values) params.add(value)
        executeDML(query, params)

    }

    fun getObjId(obj: Table, id_field: String): Int?{
        val exits_validation_fields = obj.exist_validation_fields.mapValues{
                (_, value) -> if (value!=null) value else "NULL"
        }
        val key_vals = exits_validation_fields
            .map { (key, value) -> if(value=="NULL") "$key = NULL" else "$key = ?" }
            .joinToString(" AND ")
        val query = "SELECT $id_field FROM torre_test.${obj.table_name} WHERE $key_vals"
        var params = mutableListOf<String>()
        val non_null_values = exits_validation_fields.values.filter{it!="NULL"}
        for (value in non_null_values) params.add(value)
        val rs = executeQuery(query, params) ?: return null
        while (rs.next()){
            return rs.getInt(id_field)
        }
        return null
    }
}