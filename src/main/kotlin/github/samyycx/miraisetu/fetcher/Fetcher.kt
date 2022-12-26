package github.samyycx.miraisetu.fetcher

import github.samyycx.miraisetu.vo.SetuData

/**
 * 从api获取色图的类的接口
 * 如果以下功能某些api不支持，可以直接使用SetuData的失败数据构造器返回失败数据
 * @see SetuData
 */
interface Fetcher {

    /**
     * 获取一张普通色图
     */
    fun fetchData(): SetuData

    /**
     * 获取指定tag的一张色图
     * r18也包括在tag中
     */
    fun fetchDataWithTags(tags: MutableList<String>): SetuData
    /**
     * 获取指定张普通色图
     * 如果不支持请自行关闭缓存功能，我懒得写适配
     */
    fun fetchMultipleData(count: Int): List<SetuData>

    /** 获取指定张指定tag的色图
     *  如果不支持请自行关闭缓存功能，我懒得写适配
     */
    fun fetchMultipleDataWithTags(count: Int, tags: MutableList<String>): List<SetuData>

}