package com.parallelspace.app.system_kernel.process_control.dto

data class ResultDto<T>(
    val code: Int,
    val message: String,
    val data: T?
)
