package com.parallelspace.app.business_ability.clone_manage.enums

enum class ResultEnum(val code: Int, val message: String) {
    SUCCESS(0, "Success"),
    PARAM_ERROR(1, "Parameter error"),
    START_FAILED(2, "Failed to start clone"),
    STOP_FAILED(3, "Failed to stop clone"),
    DELETE_FAILED(4, "Failed to delete clone"),
    NOT_FOUND(5, "Clone not found"),
    BACKUP_FAILED(6, "Backup failed"),
    RESTORE_FAILED(7, "Restore failed"),
    UNKNOWN_ERROR(99, "Unknown error")
}
