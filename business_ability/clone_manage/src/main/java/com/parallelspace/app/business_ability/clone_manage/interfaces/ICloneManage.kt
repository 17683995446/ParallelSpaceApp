package com.parallelspace.app.business_ability.clone_manage.interfaces

import com.parallelspace.app.business_ability.clone_manage.param.CloneManageParam
import com.parallelspace.app.business_ability.clone_manage.dto.CloneInfoDto
import com.parallelspace.app.business_ability.clone_manage.dto.ResultDto

interface ICloneManage {
    fun startClone(param: CloneManageParam): ResultDto<Boolean>
    fun stopClone(param: CloneManageParam): ResultDto<Boolean>
    fun deleteClone(param: CloneManageParam): ResultDto<Boolean>
    fun getCloneList(): ResultDto<List<CloneInfoDto>>
    fun getCloneInfo(param: CloneManageParam): ResultDto<CloneInfoDto>
    fun backupClone(param: CloneManageParam): ResultDto<Boolean>
    fun restoreClone(param: CloneManageParam): ResultDto<Boolean>
}
