package com.parallelspace.app.system_kernel.process_control.impl

import android.content.Context
import android.content.pm.PackageManager
import com.parallelspace.app.system_kernel.process_control.dto.ResultDto
import com.parallelspace.app.system_kernel.process_control.enums.ProcessResultEnum
import com.parallelspace.app.system_kernel.process_control.enums.ProcessStatusEnum
import com.parallelspace.app.system_kernel.process_control.interfaces.IProcessControl
import com.parallelspace.app.system_kernel.process_control.param.ProcessParam

class ProcessControlImpl(
    private val context: Context
) : IProcessControl {

    private val processStatusMap = mutableMapOf<String, Int>()

    override fun startProcess(param: ProcessParam): ResultDto<Boolean> {
        return try {
            val isInstalled = checkPackageInstalled(param.packageName)
            if (isInstalled) {
                updateProcessStatus(param.packageName, ProcessStatusEnum.RUNNING.value)
                ResultDto(ProcessResultEnum.SUCCESS.code, ProcessResultEnum.SUCCESS.message, true)
            } else {
                ResultDto(ProcessResultEnum.NOT_FOUND.code, ProcessResultEnum.NOT_FOUND.message, false)
            }
        } catch (e: Exception) {
            ResultDto(ProcessResultEnum.START_FAILED.code, ProcessResultEnum.START_FAILED.message, false)
        }
    }

    override fun stopProcess(param: ProcessParam): ResultDto<Boolean> {
        return try {
            updateProcessStatus(param.packageName, ProcessStatusEnum.STOPPED.value)
            ResultDto(ProcessResultEnum.SUCCESS.code, ProcessResultEnum.SUCCESS.message, true)
        } catch (e: Exception) {
            ResultDto(ProcessResultEnum.STOP_FAILED.code, ProcessResultEnum.STOP_FAILED.message, false)
        }
    }

    override fun preparePackageInstallation(param: ProcessParam): ResultDto<Boolean> {
        return try {
            val isInstalled = checkPackageInstalled(param.packageName)
            if (isInstalled) {
                ResultDto(ProcessResultEnum.INSTALL_FAILED.code, "Package already installed", false)
            } else {
                ResultDto(ProcessResultEnum.SUCCESS.code, ProcessResultEnum.SUCCESS.message, true)
            }
        } catch (e: Exception) {
            ResultDto(ProcessResultEnum.INSTALL_FAILED.code, ProcessResultEnum.INSTALL_FAILED.message, false)
        }
    }

    override fun verifyProcess(param: ProcessParam): ResultDto<Boolean> {
        val status = processStatusMap[param.packageName]
        return if (status != null && status == ProcessStatusEnum.RUNNING.value) {
            ResultDto(ProcessResultEnum.SUCCESS.code, ProcessResultEnum.SUCCESS.message, true)
        } else {
            ResultDto(ProcessResultEnum.VERIFY_FAILED.code, ProcessResultEnum.VERIFY_FAILED.message, false)
        }
    }

    override fun getProcessStatus(param: ProcessParam): ResultDto<Int> {
        val status = processStatusMap[param.packageName] ?: ProcessStatusEnum.STOPPED.value
        return ResultDto(ProcessResultEnum.SUCCESS.code, ProcessResultEnum.SUCCESS.message, status)
    }

    private fun checkPackageInstalled(packageName: String): Boolean {
        return try {
            context.packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun updateProcessStatus(packageName: String, status: Int) {
        processStatusMap[packageName] = status
    }
}
