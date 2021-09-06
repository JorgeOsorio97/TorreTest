import extract.TorreAPI
import load.DbConnection
import models.City
import models.Country
import models.Location
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

fun main() {
    DbConnection.getConnection()
    DbConnection.executeQuery("SHOW DATABASES;")
    var result: JSONObject;
    var people: JSONArray
    var next: String? = null
    var count = 0
    do{
        result =  TorreAPI.getData("people", next, 1)
        try{
            people = result.getJSONArray("results")
            next = result.getJSONObject("pagination").getString("next")
            for(person in 0 until people.length()){
                processPerson(people.getJSONObject(person))
            }
            count++
            if (count % 10 ==0) println(count)
            break
        } catch (ex: JSONException){
            ex.printStackTrace()
            println(result.keySet())
            break
        }
    } while(next!=null)
}

fun processPerson(person: JSONObject): Unit{
    println(person.getString("locationName"))
    val location_list = person.getString("locationName").split(", ")
    val city = City(null, location_list[0])
    DbConnection.insert_or_ignore(city)
    city.id = DbConnection.getObjId(city, "id")
    val country = Country(null, location_list[1])
    DbConnection.insert_or_ignore(country)
    country.id = DbConnection.getObjId(country, "id")
    val location = Location(null, name=person.getString("locationName"), city_id = city.id!!, country_id = country.id!!)
    DbConnection.insert_or_ignore(location)
    location.id = DbConnection.getObjId(location, "id")

}

