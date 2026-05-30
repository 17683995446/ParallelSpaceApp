package com.parallelspace.app.system_kernel.sandbox_isolate.interfaces

import com.parallelspace.app.system_kernel.sandbox_isolate.param.SandboxParam
import com.parallelspace.app.system_kernel.sandbox_isolate.dto.ResultDto

interface ISandboxIsolate {
    fun createSandbox(param: SandboxParam): ResultDto<Boolean>
    fun deleteSandbox(param: SandboxParam): ResultDto<Boolean>
    fun initializeSandboxData(param: SandboxParam): ResultDto<Boolean>
    fun verifySandbox(param: SandboxParam): ResultDto<Boolean>
    fun getSandboxPath(packageName: String): String
    fun generateSandboxPath(packageName: String): String
    fun backupSandboxData(param: SandboxParam, backupPath: String): ResultDto<Boolean>
    fun restoreSandboxData(param: SandboxParam, backupPath: String): ResultDto<Boolean>
}
