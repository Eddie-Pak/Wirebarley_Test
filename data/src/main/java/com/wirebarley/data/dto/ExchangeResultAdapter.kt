package com.wirebarley.data.dto

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader

class ExchangeResultAdapter {
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
                if (peeked.nextName() == "success") {
                    isSuccess = peeked.nextBoolean()
                    break
                }
                peeked.skipValue()
            }
        } catch (e: Exception) {
            // 파싱 중 문제 발생 시 예외 처리
        } finally {
            peeked.close()
        }

        return if (isSuccess) {
            successAdapter.fromJson(reader)
        } else {
            errorAdapter.fromJson(reader)
        }
    }
}