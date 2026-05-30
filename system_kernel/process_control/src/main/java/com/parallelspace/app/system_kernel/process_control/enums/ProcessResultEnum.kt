package com.parallelspace.app.system_kernel.process_control.enums

enum class ProcessResultEnum(val code: Int, val message: String) {
    SUCCESS(0, "Success"),
    START_FAILED(1, "Process start failed"),
    STOP_FAILED(2, "Process stop failed"),
    INSTALL_FAILED(3, "Package installation failed"),
    VERIFY_FAILED(4, "Process verification failed"),
    NOT_FOUND(5, "Process not found"),
    UNKNOWN_ERROR(99, "Unknown error")
}
