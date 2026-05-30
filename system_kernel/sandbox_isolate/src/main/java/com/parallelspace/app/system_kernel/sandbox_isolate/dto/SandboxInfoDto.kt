package com.parallelspace.app.system_kernel.sandbox_isolate.dto

data class SandboxInfoDto(
    val sandboxId: String,
    val packageName: String,
    val sandboxType: Int,
    val dataPath: String,
    val status: Int,
    val createTime: Long
)
