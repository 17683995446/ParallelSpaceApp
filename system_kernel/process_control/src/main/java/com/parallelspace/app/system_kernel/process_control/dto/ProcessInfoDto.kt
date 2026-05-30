package com.parallelspace.app.system_kernel.process_control.dto

data class ProcessInfoDto(
    val processId: String,
    val packageName: String,
    val processType: Int,
    val status: Int,
    val startTime: Long,
    val pid: Int
)
