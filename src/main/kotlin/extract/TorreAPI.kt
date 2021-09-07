package extract

import khttp.post
import org.json.JSONObject

object TorreAPI {
    const val base_url = "https://search.torre.co/"
    val objects = listOf("people", "opportunities")

    fun getData(object_kind: String, after: String?, size: Int?): JSONObject{
        if (!objects.contains(object_kind)) throw Exception("Object: $object_kind is not valid")
        val url = base_url + object_kind + "/_search"
        val payload = mutableMapOf<String, String>()
        if (size!=null){
            payload["size"] = size.toString()
        }
        if (after!=null){
            payload["after"] = after
        }
        var response = post(url, params=payload)
        try{
            return response.jsonObject
        } catch (ex: Exception){
            println(response.text)
        }
        return JSONObject()
    }
}