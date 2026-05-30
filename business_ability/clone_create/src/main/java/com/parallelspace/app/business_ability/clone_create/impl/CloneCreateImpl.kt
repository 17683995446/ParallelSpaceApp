package com.parallelspace.app.business_ability.clone_create.impl

import com.parallelspace.app.business_ability.clone_create.dto.CloneInfoDto
import com.parallelspace.app.business_ability.clone_create.dto.ResultDto
import com.parallelspace.app.business_ability.clone_create.enums.CloneStatusEnum
import com.parallelspace.app.business_ability.clone_create.enums.ResultEnum
import com.parallelspace.app.business_ability.clone_create.interfaces.ICloneCreate
import com.parallelspace.app.business_ability.clone_create.param.CloneCreateParam
import com.parallelspace.app.system_kernel.process_control.interfaces.IProcessControl
import com.parallelspace.app.system_kernel.sandbox_isolate.interfaces.ISandboxIsolate

class CloneCreateImpl(
    private val sandboxIsolate: ISandboxIsolate,
    private val processControl: IProcessControl
) : ICloneCreate {

    override fun createClone(param: CloneCreateParam): ResultDto<CloneInfoDto> {
        val prepareResult = prepareEnvironment(param)
        if (prepareResult.code != ResultEnum.SUCCESS.code) {
            return ResultDto(prepareResult.code, prepareResult.message, null)
        }
        val installResult = installClonePackage(param)
        if (installResult.code != ResultEnum.SUCCESS.code) {
            return ResultDto(installResult.code, installResult.message, null)
        }
        val initResult = initializeCloneData(param)
        if (initResult.code != ResultEnum.SUCCESS.code) {
            return ResultDto(initResult.code, initResult.message, null)
        }
        val verifyResult = verifyCloneCreation(param)
        if (verifyResult.code != ResultEnum.SUCCESS.code) {
            return ResultDto(verifyResult.code, verifyResult.message, null)
        }
        val cloneInfo = buildCloneInfo(param)
        return ResultDto(ResultEnum.SUCCESS.code, ResultEnum.SUCCESS.message, cloneInfo)
    }

    override fun prepareEnvironment(param: CloneCreateParam): ResultDto<Boolean> {
        val sandboxParam = buildSandboxParam(param)
        return sandboxIsolate.createSandbox(sandboxParam)
    }

    override fun installClonePackage(param: CloneCreateParam): ResultDto<Boolean> {
        val processParam = buildProcessParam(param)
        return processControl.preparePackageInstallation(processParam)
    }

    override fun initializeCloneData(param: CloneCreateParam): ResultDto<Boolean> {
        val sandboxParam = buildSandboxParam(param)
        return sandboxIsolate.initializeSandboxData(sandboxParam)
    }

    override fun verifyCloneCreation(param: CloneCreateParam): ResultDto<Boolean> {
        val verifySandbox = sandboxIsolate.verifySandbox(buildSandboxParam(param))
        val verifyProcess = processControl.verifyProcess(buildProcessParam(param))
        val success = verifySandbox.code == ResultEnum.SUCCESS.code &&
                verifyProcess.code == ResultEnum.SUCCESS.code
        return if (success) {
            ResultDto(ResultEnum.SUCCESS.code, ResultEnum.SUCCESS.message, true)
        } else {
            ResultDto(ResultEnum.VERIFY_FAILED.code, ResultEnum.VERIFY_FAILED.message, false)
        }
    }

    private fun buildCloneInfo(param: CloneCreateParam): CloneInfoDto {
        return CloneInfoDto(
            cloneId = generateCloneId(param),
            cloneName = param.cloneName,
            packageName = param.packageName,
            sourcePackageName = param.sourcePackageName,
            status = CloneStatusEnum.RUNNING.value,
            createTime = System.currentTimeMillis(),
            lastAccessTime = System.currentTimeMillis(),
            cloneDataPath = sandboxIsolate.getSandboxPath(param.packageName)
        )
    }

    private fun generateCloneId(param: CloneCreateParam): String {
        return "${param.packageName}_${System.currentTimeMillis()}"
    }

    private fun buildSandboxParam(param: CloneCreateParam): com.parallelspace.app.system_kernel.sandbox_isolate.param.SandboxParam {
        return com.parallelspace.app.system_kernel.sandbox_isolate.param.SandboxParam(
            packageName = param.packageName,
            sandboxType = param.cloneType,
            dataPath = sandboxIsolate.generateSandboxPath(param.packageName)
        )
    }

    private fun buildProcessParam(param: CloneCreateParam): com.parallelspace.app.system_kernel.process_control.param.ProcessParam {
        return com.parallelspace.app.system_kernel.process_control.param.ProcessParam(
            packageName = param.packageName,
            processType = param.cloneType,
            command = "install"
        )
    }
}
