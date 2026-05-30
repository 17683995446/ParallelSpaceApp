package com.parallelspace.app.system_kernel.sandbox_isolate.enums

enum class SandboxResultEnum(val code: Int, val message: String) {
    SUCCESS(0, "Success"),
    CREATE_FAILED(1, "Sandbox creation failed"),
    DELETE_FAILED(2, "Sandbox deletion failed"),
    INIT_FAILED(3, "Data initialization failed"),
    VERIFY_FAILED(4, "Sandbox verification failed"),
    BACKUP_FAILED(5, "Backup failed"),
    RESTORE_FAILED(6, "Restore failed"),
    PATH_ERROR(7, "Invalid sandbox path"),
    UNKNOWN_ERROR(99, "Unknown error")
}
