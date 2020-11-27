package com.iyal.idn.crudkotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PersonItem {
    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("price")
    @Expose
    var price: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("desc")
    @Expose
    var desc: String? = null

    constructor() {}
    constructor(
        id: Int,
        name: String?,
        price: String?,
        desc: String?,
        image: String?
    ) {
        this.id = id
        this.name = name
        this.price = price
        this.image = image
        this.desc = desc
    }

}