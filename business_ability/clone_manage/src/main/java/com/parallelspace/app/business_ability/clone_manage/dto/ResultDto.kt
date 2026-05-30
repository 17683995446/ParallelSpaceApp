package com.parallelspace.app.business_ability.clone_manage.dto

data class ResultDto<T>(
    val code: Int,
    val message: String,
    val data: T?
)
