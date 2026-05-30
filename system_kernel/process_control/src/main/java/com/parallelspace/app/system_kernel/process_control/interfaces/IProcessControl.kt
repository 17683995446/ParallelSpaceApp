package com.parallelspace.app.system_kernel.process_control.interfaces

import com.parallelspace.app.system_kernel.process_control.param.ProcessParam
import com.parallelspace.app.system_kernel.process_control.dto.ResultDto

interface IProcessControl {
    fun startProcess(param: ProcessParam): ResultDto<Boolean>
    fun stopProcess(param: ProcessParam): ResultDto<Boolean>
    fun preparePackageInstallation(param: ProcessParam): ResultDto<Boolean>
    fun verifyProcess(param: ProcessParam): ResultDto<Boolean>
    fun getProcessStatus(param: ProcessParam): ResultDto<Int>
}
