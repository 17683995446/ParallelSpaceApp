package com.parallelspace.app

import android.app.Application
import com.parallelspace.app.business_ability.clone_create.CloneCreateImpl
import com.parallelspace.app.business_ability.clone_manage.CloneManageImpl
import com.parallelspace.app.system_kernel.process_control.ProcessControlImpl
import com.parallelspace.app.system_kernel.sandbox_isolate.SandboxIsolateImpl

class ParallelSpaceApplication : Application() {
    lateinit var cloneCreateImpl: CloneCreateImpl
        private set
    lateinit var cloneManageImpl: CloneManageImpl
        private set
    lateinit var sandboxIsolateImpl: SandboxIsolateImpl
        private set
    lateinit var processControlImpl: ProcessControlImpl
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        initializeImpl()
    }

    private fun initializeImpl() {
        sandboxIsolateImpl = SandboxIsolateImpl(this)
        processControlImpl = ProcessControlImpl(this)
        cloneCreateImpl = CloneCreateImpl(
            sandboxIsolate = sandboxIsolateImpl,
            processControl = processControlImpl
        )
        cloneManageImpl = CloneManageImpl(
            sandboxIsolate = sandboxIsolateImpl,
            processControl = processControlImpl
        )
    }

    companion object {
        lateinit var instance: ParallelSpaceApplication
            private set
    }
}
