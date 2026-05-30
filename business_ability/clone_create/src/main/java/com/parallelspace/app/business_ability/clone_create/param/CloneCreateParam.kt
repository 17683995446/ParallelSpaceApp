package com.parallelspace.app.business_ability.clone_create.param

data class CloneCreateParam(
    val packageName: String,
    val cloneName: String,
    val cloneType: Int,
    val sourcePackageName: String
)
