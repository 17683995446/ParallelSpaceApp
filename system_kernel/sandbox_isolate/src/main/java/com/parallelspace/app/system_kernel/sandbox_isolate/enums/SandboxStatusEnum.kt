package com.parallelspace.app.system_kernel.sandbox_isolate.enums

enum class SandboxStatusEnum(val value: Int) {
    ACTIVE(1),
    INACTIVE(2),
    CREATING(3),
    DELETING(4),
    ERROR(5)
}
