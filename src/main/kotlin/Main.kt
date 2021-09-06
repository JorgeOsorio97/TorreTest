import extract.TorreAPI
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

fun main() {
//    DbConnection.getConnection()
//    DbConnection.executeQuery("SHOW DATABASES;")
    var result: JSONObject;
    var people: JSONArray
    var next: String? = null
    var count = 0
    do{
        result =  TorreAPI.getData("people", next, 100)
        try{
            people = result.getJSONArray("results")
            next = result.getJSONObject("pagination").getString("next")
            count++
            if (count % 10 ==0) println(count)
        } catch (ex: JSONException){
            ex.printStackTrace()
            println(result.keySet())
            break
        }
    } while(next!=null)
}

fun clean_person(person: JSONObject){

}

