package com.wirebarley.data.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.ToJson
import com.wirebarley.data.dto.ExchangeApiResponse
import com.wirebarley.data.dto.ExchangeErrorResponse
import com.wirebarley.data.dto.ExchangeSuccessResponse

class ExchangeApiResponseAdapter {
    private val options = JsonReader.Options.of("success")

    @FromJson
    fun fromJson(
        reader: JsonReader,
        successAdapter: JsonAdapter<ExchangeSuccessResponse>,
        errorAdapter: JsonAdapter<ExchangeErrorResponse>
    ): ExchangeApiResponse? {
        val peeked = reader.peekJson()

        var isSuccess = false

        try {
            peeked.beginObject()
            while (peeked.hasNext()) {
                when (peeked.selectName(options)) {
                    0 -> {
                        isSuccess = peeked.nextBoolean()
                        break
                    }

                    else -> {
                        peeked.skipName()
                        peeked.skipValue()
                    }
                }
            }
        } finally {
            peeked.close()
        }

        return if (isSuccess) {
            successAdapter.fromJson(reader)
        } else {
            errorAdapter.fromJson(reader)
        }
    }

    @ToJson
    fun toJson(
        writer: com.squareup.moshi.JsonWriter,
        value: ExchangeApiResponse?,
        successAdapter: JsonAdapter<ExchangeSuccessResponse>,
        errorAdapter: JsonAdapter<ExchangeErrorResponse>
    ) {
        when (value) {
            is ExchangeSuccessResponse -> successAdapter.toJson(writer, value)
            is ExchangeErrorResponse -> errorAdapter.toJson(writer, value)
            null -> writer.nullValue()
        }
    }
}