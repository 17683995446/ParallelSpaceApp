package com.parallelspace.app.business_ability.clone_create.enums

enum class ResultEnum(val code: Int, val message: String) {
    SUCCESS(0, "Success"),
    PARAM_ERROR(1, "Parameter error"),
    CREATE_FAILED(2, "Clone creation failed"),
    INSTALL_FAILED(3, "Package installation failed"),
    DATA_INIT_FAILED(4, "Data initialization failed"),
    VERIFY_FAILED(5, "Verification failed"),
    UNKNOWN_ERROR(99, "Unknown error")
}
