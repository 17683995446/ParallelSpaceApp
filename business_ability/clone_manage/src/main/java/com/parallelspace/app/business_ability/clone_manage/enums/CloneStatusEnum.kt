package com.parallelspace.app.business_ability.clone_manage.enums

enum class CloneStatusEnum(val value: Int) {
    RUNNING(1),
    STOPPED(2),
    CREATING(3),
    DELETING(4),
    ERROR(5)
}
