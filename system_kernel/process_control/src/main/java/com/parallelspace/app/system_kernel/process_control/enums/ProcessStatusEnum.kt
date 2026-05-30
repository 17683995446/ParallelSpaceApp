package com.parallelspace.app.system_kernel.process_control.enums

enum class ProcessStatusEnum(val value: Int) {
    RUNNING(1),
    STOPPED(2),
    STARTING(3),
    STOPPING(4),
    ERROR(5)
}
