package com.mymyeong.webfluxtest.weather

import com.fasterxml.jackson.annotation.JsonProperty

data class WeatherMidFcst(
    @JsonProperty("response") var response: WeatherResponse,
)

data class WeatherResponse(
    @JsonProperty("header") val midFcstHeader: MidFcstHeader,
    @JsonProperty("body") val body: MidFcstBody,
)

data class MidFcstHeader(
    @JsonProperty("resultCode") val resultCode: String,
    @JsonProperty("resultMsg") val resultMsg: String
)

data class MidFcstBody(
    @JsonProperty("dataType") val dataType: String,
    @JsonProperty("items") val items: MidFcstItems,
    @JsonProperty("pageNo") val pageNo: Int,
    @JsonProperty("numOfRows") val numOfRows: Int,
    @JsonProperty("totalCount") val totalCount: Int,
)

data class MidFcstItems(
    @JsonProperty("item") val item: List<MidFcstItem>
)

data class MidFcstItem(
    @JsonProperty("wfSv") val wfSv: String
)