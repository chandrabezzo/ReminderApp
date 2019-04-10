package com.bezzo.utama.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NotificationResponse {
    @SerializedName("multicast_id")
    @Expose
    var multicastId: Long? = null
    @SerializedName("success")
    @Expose
    var success: Int? = null
    @SerializedName("failure")
    @Expose
    var failure: Int? = null
    @SerializedName("canonical_ids")
    @Expose
    var canonicalIds: Int? = null
    @SerializedName("results")
    @Expose
    var results: List<Result>? = null
}

class Result {
    @SerializedName("message_id")
    @Expose
    var messageId: String? = null
}

class NotificationRequest {
    @SerializedName("to")
    @Expose
    var to: String? = null
    @SerializedName("data")
    @Expose
    var data: Data? = null
}

class Data {
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
}