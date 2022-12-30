package github.samyycx.miraisetu.fetcher.impl

import com.alibaba.fastjson.JSONObject
import github.samyycx.miraisetu.fetcher.Fetcher
import github.samyycx.miraisetu.vo.SetuData
import org.apache.http.client.methods.HttpGet
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import java.lang.UnsupportedOperationException
import java.util.*

class WaifuIMFetcher : Fetcher {

    private val api = "https://api.waifu.im/search?"
    private val client = HttpClientBuilder.create().build()

    override fun fetchData(): SetuData {
        val httpGet = HttpGet(api)
        return fetchData(httpGet)
    }

    override fun fetchDataWithTags(tags: MutableList<String>): SetuData {
        var link = api
        if (tags.contains("nsfw")) {
            link = link.plus("is_nsfw=true&")
            tags.remove("nsfw")
        }
        for (tag in tags) {
            link = link.plus("included_tags=${tag}&")
        }
        return fetchData(HttpGet(link))
    }

    // 这玩意没有支持的必要
    override fun fetchMultipleData(count: Int): List<SetuData> {
        throw UnsupportedOperationException()
    }

    override fun fetchMultipleDataWithTags(count: Int, tags: MutableList<String>): List<SetuData> {
        throw UnsupportedOperationException()
    }

    private fun fetchData(httpGet: HttpGet) : SetuData {
        val resp = client.execute(httpGet)
        return if (resp.statusLine.statusCode == 200) {
            val jsonString = EntityUtils.toString(resp.entity)
            val json = JSONObject.parseObject(jsonString)
            val body = json.getJSONArray("images").getJSONObject(0)
            val source = body.getString("source")
            val url = body.getString("url")
            if (url == null) {
                SetuData("找不到匹配的tag，请重试")
            }
            SetuData(Properties().apply {
                put("来源", source)
            }, url)

        } else {
            SetuData("接口调取失败,错误原因:${resp.statusLine.statusCode} ${resp.statusLine.reasonPhrase}")
        }
    }


}