package com.parallelspace.app.business_ability.clone_manage.impl

import com.parallelspace.app.business_ability.clone_manage.dto.CloneInfoDto
import com.parallelspace.app.business_ability.clone_manage.dto.ResultDto
import com.parallelspace.app.business_ability.clone_manage.enums.CloneStatusEnum
import com.parallelspace.app.business_ability.clone_manage.enums.ResultEnum
import com.parallelspace.app.business_ability.clone_manage.interfaces.ICloneManage
import com.parallelspace.app.business_ability.clone_manage.param.CloneManageParam
import com.parallelspace.app.system_kernel.process_control.interfaces.IProcessControl
import com.parallelspace.app.system_kernel.process_control.param.ProcessParam
import com.parallelspace.app.system_kernel.sandbox_isolate.interfaces.ISandboxIsolate
import com.parallelspace.app.system_kernel.sandbox_isolate.param.SandboxParam

class CloneManageImpl(
    private val sandboxIsolate: ISandboxIsolate,
    private val processControl: IProcessControl
) : ICloneManage {

    private val cloneList = mutableListOf<CloneInfoDto>()

    override fun startClone(param: CloneManageParam): ResultDto<Boolean> {
        val processParam = buildProcessParam(param, "start")
        val startResult = processControl.startProcess(processParam)
        return if (startResult.code == ResultEnum.SUCCESS.code) {
            updateCloneStatus(param.cloneId, CloneStatusEnum.RUNNING.value)
            ResultDto(ResultEnum.SUCCESS.code, ResultEnum.SUCCESS.message, true)
        } else {
            ResultDto(startResult.code, startResult.message, false)
        }
    }

    override fun stopClone(param: CloneManageParam): ResultDto<Boolean> {
        val processParam = buildProcessParam(param, "stop")
        val stopResult = processControl.stopProcess(processParam)
        return if (stopResult.code == ResultEnum.SUCCESS.code) {
            updateCloneStatus(param.cloneId, CloneStatusEnum.STOPPED.value)
            ResultDto(ResultEnum.SUCCESS.code, ResultEnum.SUCCESS.message, true)
        } else {
            ResultDto(stopResult.code, stopResult.message, false)
        }
    }

    override fun deleteClone(param: CloneManageParam): ResultDto<Boolean> {
        val sandboxParam = buildSandboxParam(param)
        val deleteSandbox = sandboxIsolate.deleteSandbox(sandboxParam)
        val processParam = buildProcessParam(param, "uninstall")
        val uninstallProcess = processControl.stopProcess(processParam)
        return if (deleteSandbox.code == ResultEnum.SUCCESS.code) {
            removeFromCloneList(param.cloneId)
            ResultDto(ResultEnum.SUCCESS.code, ResultEnum.SUCCESS.message, true)
        } else {
            ResultDto(deleteSandbox.code, deleteSandbox.message, false)
        }
    }

    override fun getCloneList(): ResultDto<List<CloneInfoDto>> {
        return ResultDto(ResultEnum.SUCCESS.code, ResultEnum.SUCCESS.message, cloneList.toList())
    }

    override fun getCloneInfo(param: CloneManageParam): ResultDto<CloneInfoDto> {
        val clone = cloneList.find { it.cloneId == param.cloneId }
        return if (clone != null) {
            ResultDto(ResultEnum.SUCCESS.code, ResultEnum.SUCCESS.message, clone)
        } else {
            ResultDto(ResultEnum.NOT_FOUND.code, ResultEnum.NOT_FOUND.message, null)
        }
    }

    override fun backupClone(param: CloneManageParam): ResultDto<Boolean> {
        val sandboxParam = buildSandboxParam(param)
        val backupPath = "${sandboxParam.dataPath}_backup"
        return sandboxIsolate.backupSandboxData(sandboxParam, backupPath)
    }

    override fun restoreClone(param: CloneManageParam): ResultDto<Boolean> {
        val sandboxParam = buildSandboxParam(param)
        val backupPath = "${sandboxParam.dataPath}_backup"
        return sandboxIsolate.restoreSandboxData(sandboxParam, backupPath)
    }

    private fun buildProcessParam(param: CloneManageParam, command: String): ProcessParam {
        return ProcessParam(
            packageName = param.packageName,
            processType = param.operationType,
            command = command
        )
    }

    private fun buildSandboxParam(param: CloneManageParam): SandboxParam {
        return SandboxParam(
            packageName = param.packageName,
            sandboxType = param.operationType,
            dataPath = sandboxIsolate.getSandboxPath(param.packageName)
        )
    }

    private fun updateCloneStatus(cloneId: String, status: Int) {
        val index = cloneList.indexOfFirst { it.cloneId == cloneId }
        if (index >= 0) {
            cloneList[index] = cloneList[index].copy(status = status)
        }
    }

    private fun removeFromCloneList(cloneId: String) {
        cloneList.removeAll { it.cloneId == cloneId }
    }
}
