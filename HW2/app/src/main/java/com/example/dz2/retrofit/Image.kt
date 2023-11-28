package com.example.dz2.retrofit

data class Image(
    val id: Int,
    val title: String, // only title was updated
    val description: String,
    val price: Int,
    val discountPercentage: Float,
    val rating: Float,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>
)