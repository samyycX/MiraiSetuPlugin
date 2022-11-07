package github.samyycx.miraisetu.fetcher.impl

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import github.samyycx.miraisetu.fetcher.SetuFetcher
import github.samyycx.miraisetu.vo.SetuData
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import java.util.*

class LoliconAppFetcher : SetuFetcher {

    private val api = "https://api.lolicon.app/setu/v2"
    private val client = HttpClientBuilder.create().build()

    override fun fetchData(): SetuData {
        val httpPost = HttpPost(api)
        return fetchData(httpPost)[0]
    }

    override fun fetchDataWithTags(tags: MutableList<String>): SetuData {
        val httpPost = HttpPost(api)
        val reqBody = JSONObject()
        if (tags.contains("r18")) {
            reqBody["r18"] = 1
            tags.remove("r18")
        }
        val tagsArray = JSONArray()
        tagsArray.addAll(tags)
        reqBody["tag"] = tagsArray

        return fetchData(httpPost, reqBody)[0]

    }

    override fun fetchMultipleData(count: Int): List<SetuData> {

        // LoliconApp接口只支持单次获取最多20次
        if (count <= 20) {
            val httpPost = HttpPost(api)
            val reqBody = JSONObject()

            reqBody["count"] = count
            val entity = StringEntity(reqBody.toString(), ContentType.APPLICATION_JSON)
            httpPost.entity = entity
            return fetchData(httpPost)
        }

        val result = mutableListOf<SetuData>()
        for (i in 0..(count/20)+1) {
            val httpPost = HttpPost(api)
            val reqBody = JSONObject()

            if (i == count/20) {
                reqBody["count"] = count % 20
            } else {
                reqBody["count"] = 20
            }
            result.addAll(fetchData(httpPost, reqBody))
        }

        return result

    }

    override fun fetchMultipleDataWithTags(count: Int, tags: MutableList<String>): List<SetuData> {

        val basicHttpPost = HttpPost(api)
        val basicReqBody = JSONObject()
        if (tags.contains("r18")) {
            basicReqBody["r18"] = 1
            tags.remove("r18")
        }
        val tagsArray = JSONArray()
        tagsArray.addAll(tags)
        basicReqBody["tag"] = tagsArray
        //val entity = StringEntity(basicReqBody.toString(), ContentType.APPLICATION_JSON)
        //basicHttpPost.entity = entity

        // LoliconApp接口只支持单次获取最多20次
        if (count <= 20) {
            val httpPost = basicHttpPost.clone() as HttpPost
            val reqBody = basicReqBody.clone() as JSONObject

            reqBody["count"] = count
            return fetchData(httpPost, reqBody)
        }

        val result = mutableListOf<SetuData>()
        for (i in 0..(count/20)+1) {
            val httpPost = basicHttpPost.clone() as HttpPost
            val reqBody = basicReqBody.clone() as JSONObject

            if (i == count/20) {
                reqBody["count"] = count % 20
            } else {
                reqBody["count"] = 20
            }
            result.addAll(fetchData(httpPost, reqBody))
        }

        return result
    }

    fun fetchData(post: HttpPost, body: JSONObject = JSONObject()): List<SetuData> {

        body["size"] = "regular"
        post.entity = StringEntity(body.toString(), ContentType.APPLICATION_JSON)
        val resp = client.execute(post)

        if (resp.statusLine.statusCode != 200) {
            return mutableListOf(SetuData("请求API时出现问题: ${resp.statusLine.statusCode} ${resp.statusLine.reasonPhrase}"))
        }

        val jsonString = EntityUtils.toString(resp.entity)
        val json = JSONObject.parseObject(jsonString)

        val err = json.getString("error")
        if (!err.equals("")) return mutableListOf(SetuData(err))

        val dataArray = json.getJSONArray("data")
        val setuDataList = mutableListOf<SetuData>()

        for (x in 0 until dataArray.size) {
            val data = dataArray.getJSONObject(x)
            val pid = data.getIntValue("pid").toString()
            val author = data.getString("author")
            val imageUrl = data.getJSONObject("urls").getString("regular")

            val properties = Properties()
            properties["pid"] = pid
            properties["画师"] = author

            setuDataList.add(SetuData(properties, imageUrl))
        }
        return setuDataList

    }

}