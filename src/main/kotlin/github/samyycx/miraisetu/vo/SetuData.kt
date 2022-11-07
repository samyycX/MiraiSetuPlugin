package github.samyycx.miraisetu.vo

import java.util.*

data class SetuData(
        val success: Boolean,
        val properties: Properties,
        val imageUrl: String,
        val err: String
) {
    constructor(properties: Properties, imageUrl: String): this(true, properties, imageUrl, "")
    constructor(err: String): this(false, Properties(), "", err)
}
