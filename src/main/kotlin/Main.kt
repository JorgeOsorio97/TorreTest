import extract.TorreAPI
import load.DbConnection
import models.City
import models.Country
import models.Location
import models.Person
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

fun main() {
    DbConnection.getConnection()
    // DbConnection.executeQuery("SHOW DATABASES;", mutableListOf())
    var result: JSONObject;
    var people: JSONArray
    var next: String? = null
    var count = 0
    do{
        result =  TorreAPI.getData("people", next, 100)
        try{
            people = result.getJSONArray("results")
            next = result.getJSONObject("pagination").getString("next")
            for(person in 0 until people.length()){
                processPerson(people.getJSONObject(person))
            }
            count++
            if (count % 10 ==0) println(count)
        } catch (ex: JSONException){
            ex.printStackTrace()
            println(result.keySet())
            next = null
        }
    } while(next!=null)
}

fun processPerson(person: JSONObject): Unit{
    // Location
    // city
    var stage = "location"
    var location: Location? = null
    try{
        if (!person.isNull("locationName")){
            val location_list = person.getString("locationName").split(", ")

            if(location_list.size>1) {
                stage = "city-size>1"
                val city = City(null, location_list[0])
                DbConnection.insert_or_ignore(city)
                city.id = DbConnection.getObjId(city, "id")
                // country
                stage = "country-size>1"
                val country = Country(null, location_list[1])
                DbConnection.insert_or_ignore(country)
                country.id = DbConnection.getObjId(country, "id")
                // location
                stage = "location-size>1"
                location =
                    Location(null, name = person.getString("locationName"), city_id = city.id!!, country_id = country.id!!)
                DbConnection.insert_or_ignore(location)
                location.id = DbConnection.getObjId(location, "id")
            } else {
                // country
                stage = "countru-size0"
                val country = Country(null, location_list[0])
                DbConnection.insert_or_ignore(country)
                country.id = DbConnection.getObjId(country, "id")
                // location
                stage = "location-size0"
                location =
                    Location(null, name = person.getString("locationName"), city_id = null, country_id = country.id!!)
                DbConnection.insert_or_ignore(location)
                location.id = DbConnection.getObjId(location, "id")
            }
        }


        //openTo TODO: future work
        // val openTo = person.getJSONArray("openTo")

        // People
        val location_id = if (location==null) null else location.id
        stage = "person"
        Person(
            subjectId = person.getInt("subjectId"),
            verified = person.getBoolean("verified"),
            weight = person.getDouble("weight").toFloat(),
            location = location_id,
            openTo = null
        )
    } catch (ex: Exception){
        ex.printStackTrace()
        println(person)
        println(stage)
        throw ex
    }
}

