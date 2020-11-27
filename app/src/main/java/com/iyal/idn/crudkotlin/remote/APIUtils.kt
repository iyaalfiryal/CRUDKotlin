package com.iyal.idn.crudkotlin.remote

object APIUtils {
    private const val API_URL = "http://192.168.100.157/marketplace/index.php/"
    val productService: ProductService
        get() = RetrofitClient.getClient(API_URL)?.create(ProductService::class.java)!!
}