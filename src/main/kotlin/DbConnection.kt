import java.sql.*
import java.util.*

object DbConnection {
    var conn: Connection? = null
    internal const val username = "torre_test"
    internal const val password = "torre_test"


    fun executeQuery(query: String) {
        var stmt: Statement? = null
        var result: ResultSet? = null

        try {
            stmt = conn!!.createStatement()
            result = stmt!!.executeQuery(query)

            if (stmt.execute(query)) {
                result = stmt.resultSet
            }
            while (result!!.next()) {
                println(result.getString("Database"))
            }
        }catch (ex: SQLException) {
            println("SQL Error")
            ex.printStackTrace()
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
}