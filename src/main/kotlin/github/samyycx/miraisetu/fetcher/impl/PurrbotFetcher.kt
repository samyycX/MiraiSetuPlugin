package github.samyycx.miraisetu.fetcher.impl

import com.alibaba.fastjson.JSONObject
import github.samyycx.miraisetu.fetcher.Fetcher
import github.samyycx.miraisetu.vo.SetuData
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import java.util.*

class PurrbotFetcher : Fetcher{

    private val api = "https://purrbot.site/api/img"
    private val client = HttpClientBuilder.create().build()

    // 直接写一个最简单的，反正manager里会封装
    override fun fetchData(): SetuData {
        val link = api.plus("/sfw/neko/img")
        return fetchData(link)
    }

    // 直接写死规则了, tags只能有两个，第一个是sfw/nsfw，第二个是tag
    override fun fetchDataWithTags(tags: MutableList<String>): SetuData {
        if (tags[0] == "nsfw") tags[1] = "neko"
        val link = api.plus("/${tags[0]}/${tags[1]}/img")
        return fetchData(link)
    }

    override fun fetchMultipleData(count: Int): List<SetuData> {
        TODO("Not yet implemented")
    }

    override fun fetchMultipleDataWithTags(count: Int, tags: MutableList<String>): List<SetuData> {
        TODO("Not yet implemented")
    }

    private fun fetchData(url: String) : SetuData {
        val httpGet = HttpGet(url)
        val resp = client.execute(httpGet)

        if (resp.statusLine.statusCode != 200) {
            return SetuData("接口调取失败,错误原因:${resp.statusLine.statusCode} ${resp.statusLine.reasonPhrase}")
        }
        val jsonString = EntityUtils.toString(resp.entity)
        val json = JSONObject.parseObject(jsonString)
        if (json.getBoolean("error")) {
            return SetuData("purrbot接口出现未知错误")
        }

        val imageUrl = json.getString("link")
        return SetuData(Properties(), imageUrl)
    }
}