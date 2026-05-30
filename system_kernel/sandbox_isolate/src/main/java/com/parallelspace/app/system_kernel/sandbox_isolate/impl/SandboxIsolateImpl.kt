package com.parallelspace.app.system_kernel.sandbox_isolate.impl

import android.content.Context
import com.parallelspace.app.system_kernel.sandbox_isolate.dto.ResultDto
import com.parallelspace.app.system_kernel.sandbox_isolate.enums.SandboxResultEnum
import com.parallelspace.app.system_kernel.sandbox_isolate.interfaces.ISandboxIsolate
import com.parallelspace.app.system_kernel.sandbox_isolate.param.SandboxParam
import java.io.File

class SandboxIsolateImpl(
    private val context: Context
) : ISandboxIsolate {

    private val baseSandboxPath: String
        get() = context.filesDir.absolutePath + "/sandbox/"

    override fun createSandbox(param: SandboxParam): ResultDto<Boolean> {
        val sandboxDir = File(param.dataPath)
        return if (sandboxDir.exists() || sandboxDir.mkdirs()) {
            initializeSandboxDirectories(param.dataPath)
            ResultDto(SandboxResultEnum.SUCCESS.code, SandboxResultEnum.SUCCESS.message, true)
        } else {
            ResultDto(SandboxResultEnum.CREATE_FAILED.code, SandboxResultEnum.CREATE_FAILED.message, false)
        }
    }

    override fun deleteSandbox(param: SandboxParam): ResultDto<Boolean> {
        val sandboxDir = File(param.dataPath)
        return if (!sandboxDir.exists()) {
            ResultDto(SandboxResultEnum.SUCCESS.code, SandboxResultEnum.SUCCESS.message, true)
        } else if (sandboxDir.deleteRecursively()) {
            ResultDto(SandboxResultEnum.SUCCESS.code, SandboxResultEnum.SUCCESS.message, true)
        } else {
            ResultDto(SandboxResultEnum.DELETE_FAILED.code, SandboxResultEnum.DELETE_FAILED.message, false)
        }
    }

    override fun initializeSandboxData(param: SandboxParam): ResultDto<Boolean> {
        val sandboxDir = File(param.dataPath)
        return if (sandboxDir.exists() || sandboxDir.mkdirs()) {
            ResultDto(SandboxResultEnum.SUCCESS.code, SandboxResultEnum.SUCCESS.message, true)
        } else {
            ResultDto(SandboxResultEnum.INIT_FAILED.code, SandboxResultEnum.INIT_FAILED.message, false)
        }
    }

    override fun verifySandbox(param: SandboxParam): ResultDto<Boolean> {
        val sandboxDir = File(param.dataPath)
        val isValid = sandboxDir.exists() && sandboxDir.isDirectory && sandboxDir.canRead()
        return if (isValid) {
            ResultDto(SandboxResultEnum.SUCCESS.code, SandboxResultEnum.SUCCESS.message, true)
        } else {
            ResultDto(SandboxResultEnum.VERIFY_FAILED.code, SandboxResultEnum.VERIFY_FAILED.message, false)
        }
    }

    override fun getSandboxPath(packageName: String): String {
        return generateSandboxPath(packageName)
    }

    override fun generateSandboxPath(packageName: String): String {
        return baseSandboxPath + packageName + "/"
    }

    override fun backupSandboxData(param: SandboxParam, backupPath: String): ResultDto<Boolean> {
        val sourceDir = File(param.dataPath)
        val targetDir = File(backupPath)
        return if (sourceDir.exists()) {
            copyDirectory(sourceDir, targetDir)
            ResultDto(SandboxResultEnum.SUCCESS.code, SandboxResultEnum.SUCCESS.message, true)
        } else {
            ResultDto(SandboxResultEnum.BACKUP_FAILED.code, SandboxResultEnum.BACKUP_FAILED.message, false)
        }
    }

    override fun restoreSandboxData(param: SandboxParam, backupPath: String): ResultDto<Boolean> {
        val sourceDir = File(backupPath)
        val targetDir = File(param.dataPath)
        return if (sourceDir.exists()) {
            copyDirectory(sourceDir, targetDir)
            ResultDto(SandboxResultEnum.SUCCESS.code, SandboxResultEnum.SUCCESS.message, true)
        } else {
            ResultDto(SandboxResultEnum.RESTORE_FAILED.code, SandboxResultEnum.RESTORE_FAILED.message, false)
        }
    }

    private fun initializeSandboxDirectories(basePath: String) {
        File(basePath, "data").mkdirs()
        File(basePath, "cache").mkdirs()
        File(basePath, "files").mkdirs()
        File(basePath, "shared_prefs").mkdirs()
        File(basePath, "databases").mkdirs()
    }

    private fun copyDirectory(source: File, target: File) {
        if (!target.exists()) {
            target.mkdirs()
        }
        source.listFiles()?.forEach { file ->
            val destFile = File(target, file.name)
            if (file.isDirectory) {
                copyDirectory(file, destFile)
            } else {
                file.copyTo(destFile, overwrite = true)
            }
        }
    }
}
