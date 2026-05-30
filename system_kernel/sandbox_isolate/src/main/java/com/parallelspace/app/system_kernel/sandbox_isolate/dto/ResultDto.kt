package com.parallelspace.app.system_kernel.sandbox_isolate.dto

data class ResultDto<T>(
    val code: Int,
    val message: String,
    val data: T?
)
