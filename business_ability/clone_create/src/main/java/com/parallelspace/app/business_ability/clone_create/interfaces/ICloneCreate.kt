package com.parallelspace.app.business_ability.clone_create.interfaces

import com.parallelspace.app.business_ability.clone_create.param.CloneCreateParam
import com.parallelspace.app.business_ability.clone_create.dto.CloneInfoDto
import com.parallelspace.app.business_ability.clone_create.dto.ResultDto

interface ICloneCreate {
    fun createClone(param: CloneCreateParam): ResultDto<CloneInfoDto>
    fun prepareEnvironment(param: CloneCreateParam): ResultDto<Boolean>
    fun installClonePackage(param: CloneCreateParam): ResultDto<Boolean>
    fun initializeCloneData(param: CloneCreateParam): ResultDto<Boolean>
    fun verifyCloneCreation(param: CloneCreateParam): ResultDto<Boolean>
}
