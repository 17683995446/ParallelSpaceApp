pluginManagement {
    repositories {
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "ParallelSpaceApp"
include(":app")
include(":business_ability:clone_create")
include(":business_ability:clone_manage")
include(":system_kernel:sandbox_isolate")
include(":system_kernel:process_control")
