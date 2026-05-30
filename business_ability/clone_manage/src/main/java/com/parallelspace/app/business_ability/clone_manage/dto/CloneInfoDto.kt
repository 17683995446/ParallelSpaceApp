package com.parallelspace.app.business_ability.clone_manage.dto

data class CloneInfoDto(
    val cloneId: String,
    val cloneName: String,
    val packageName: String,
    val sourcePackageName: String,
    val status: Int,
    val createTime: Long,
    val lastAccessTime: Long,
    val cloneDataPath: String
)
