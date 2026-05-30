# 安卓分身APP交付文档

## 项目概述

**项目名称**: ParallelSpaceApp（安卓分身APP）  
**项目类型**: Android Native Application  
**架构**: 二叉树模块化架构  
**语言**: Kotlin  
**最低SDK**: Android 8.0 (API 26)  
**目标SDK**: Android 14 (API 34)

---

## 交付清单

### 1. 源代码包
- [ ] 完整项目源代码
- [ ] Gradle构建配置
- [ ] Android资源文件
- [ ] 模块结构完整

### 2. 文档
- [ ] 项目结构文档 (PROJECT_STRUCTURE.md)
- [ ] 构建指南 (BUILD_GUIDE.md)
- [ ] 实现计划 (IMPLEMENTATION_PLAN.md)
- [ ] 本文档

### 3. APK（需在本地环境构建）
- [ ] Debug版本APK
- [ ] Release版本APK（需签名配置）

---

## 快速开始

### 环境要求

**必需**:
- JDK 17 或更高版本
- Android SDK (API 26-34)
- 网络连接（下载Gradle依赖）

**可选**:
- Android Studio（推荐IDE）
- Git（版本控制）

### 构建步骤

#### 方式一：命令行构建

```bash
# 1. 克隆项目
git clone <repository-url>
cd ParallelSpaceApp

# 2. 设置环境变量
export ANDROID_HOME=/path/to/android-sdk
export JAVA_HOME=/path/to/jdk-17

# 3. 构建Debug APK
./gradlew assembleDebug

# 4. 查找APK
ls -lh app/build/outputs/apk/debug/app-debug.apk
```

#### 方式二：Android Studio

1. 打开Android Studio
2. 选择 "Open an existing project"
3. 选择项目根目录
4. 等待Gradle同步完成
5. 点击 Run > Run 'app'

### 构建变体

```bash
# Debug版本（可调试）
./gradlew assembleDebug

# Release版本（优化、混淆、签名）
./gradlew assembleRelease

# 清理构建
./gradlew clean

# 重新下载依赖
./gradlew --refresh-dependencies
```

---

## 项目结构详解

```
ParallelSpaceApp/
│
├── app/                              # 主应用程序模块
│   ├── build.gradle.kts              # 应用级构建配置
│   ├── proguard-rules.pro            # ProGuard混淆规则
│   └── src/main/
│       ├── AndroidManifest.xml       # 应用清单文件
│       ├── java/com/parallelspace/app/
│       │   ├── ParallelSpaceApplication.kt  # 应用入口
│       │   └── ui/                   # UI层
│       │       ├── MainActivity.kt
│       │       ├── CloneListFragment.kt
│       │       ├── CloneDetailFragment.kt
│       │       ├── CreateCloneFragment.kt
│       │       ├── SettingsFragment.kt
│       │       └── CloneListAdapter.kt
│       └── res/                      # 资源文件
│           ├── layout/               # 布局文件
│           ├── menu/                 # 菜单文件
│           └── values/               # 字符串、颜色、主题
│
├── business_ability/                 # 业务能力模块
│   ├── clone_create/                 # 分身创建子模块
│   │   ├── build.gradle.kts
│   │   └── src/main/java/
│   │       └── com/parallelspace/app/business_ability/clone_create/
│   │           ├── interfaces/       # 公开接口
│   │           ├── impl/           # 私有实现
│   │           ├── dto/             # 数据传输对象
│   │           ├── param/           # 参数对象
│   │           └── enums/           # 枚举定义
│   └── clone_manage/                # 分身管理子模块
│       └── ... (结构同上)
│
├── system_kernel/                    # 系统内核模块
│   ├── sandbox_isolate/             # 沙盒隔离子模块
│   │   └── ... (结构同上)
│   └── process_control/             # 进程调度子模块
│       └── ... (结构同上)
│
├── build.gradle.kts                  # 根级构建配置
├── settings.gradle.kts               # 项目设置
├── gradle.properties                # Gradle属性
├── gradlew                          # Gradle Wrapper (Unix)
├── gradlew.bat                      # Gradle Wrapper (Windows)
└── local.properties                 # 本地SDK路径
```

---

## 模块说明

### 1. business_ability（业务能力层）

#### clone_create - 分身创建
**职责**：创建应用分身实例
**核心接口**：
```kotlin
interface ICloneCreate {
    fun createClone(param: CloneCreateParam): ResultDto<CloneInfoDto>
    fun prepareEnvironment(param: CloneCreateParam): ResultDto<Boolean>
    fun installClonePackage(param: CloneCreateParam): ResultDto<Boolean>
    fun initializeCloneData(param: CloneCreateParam): ResultDto<Boolean>
    fun verifyCloneCreation(param: CloneCreateParam): ResultDto<Boolean>
}
```

#### clone_manage - 分身管理
**职责**：管理分身生命周期
**核心接口**：
```kotlin
interface ICloneManage {
    fun startClone(param: CloneManageParam): ResultDto<Boolean>
    fun stopClone(param: CloneManageParam): ResultDto<Boolean>
    fun deleteClone(param: CloneManageParam): ResultDto<Boolean>
    fun getCloneList(): ResultDto<List<CloneInfoDto>>
    fun getCloneInfo(param: CloneManageParam): ResultDto<CloneInfoDto>
    fun backupClone(param: CloneManageParam): ResultDto<Boolean>
    fun restoreClone(param: CloneManageParam): ResultDto<Boolean>
}
```

### 2. system_kernel（系统内核层）

#### sandbox_isolate - 沙盒隔离
**职责**：提供独立的存储和运行环境
**核心接口**：
```kotlin
interface ISandboxIsolate {
    fun createSandbox(param: SandboxParam): ResultDto<Boolean>
    fun deleteSandbox(param: SandboxParam): ResultDto<Boolean>
    fun initializeSandboxData(param: SandboxParam): ResultDto<Boolean>
    fun verifySandbox(param: SandboxParam): ResultDto<Boolean>
    fun backupSandboxData(param: SandboxParam, backupPath: String): ResultDto<Boolean>
    fun restoreSandboxData(param: SandboxParam, backupPath: String): ResultDto<Boolean>
}
```

#### process_control - 进程调度
**职责**：管理分身进程
**核心接口**：
```kotlin
interface IProcessControl {
    fun startProcess(param: ProcessParam): ResultDto<Boolean>
    fun stopProcess(param: ProcessParam): ResultDto<Boolean>
    fun preparePackageInstallation(param: ProcessParam): ResultDto<Boolean>
    fun verifyProcess(param: ProcessParam): ResultDto<Boolean>
    fun getProcessStatus(param: ProcessParam): ResultDto<Int>
}
```

---

## 数据流

```
用户操作
  ↓
UI层 (Fragment/Activity)
  ↓
业务层 (ICloneCreate/ICloneManage)
  ↓
内核层 (ISandboxIsolate/IProcessControl)
  ↓
系统层 (Android API)
```

### 创建分身流程

```
1. 用户输入分身信息
   ↓
2. CloneListFragment → CreateCloneFragment
   ↓
3. 调用 CloneCreateImpl.createClone(param)
   ↓
4. 依次调用：
   ├→ ISandboxIsolate.createSandbox()     创建隔离空间
   ├→ IProcessControl.preparePackageInstallation()  准备安装
   ├→ ISandboxIsolate.initializeSandboxData()  初始化数据
   └→ ISandboxIsolate.verifySandbox()     验证创建
   ↓
5. 返回 ResultDto<CloneInfoDto>
   ↓
6. UI显示结果
```

---

## 技术规范

### 命名规范
- **源文件**: PascalCase (大驼峰)
- **接口**: I前缀 + PascalCase
- **方法**: camelCase (小驼峰)
- **包名**: 全小写

### 目录结构
每个模块必须包含：
```
interfaces/    # 公开接口（可被其他模块访问）
impl/          # 私有实现（编译级私有）
dto/           # 数据传输对象
param/         # 参数载体
enums/         # 枚举定义
```

### 代码规范
- 方法行数 ≤ 6行
- 参数数量 ≤ 3个
- 必须有类型注解
- 禁止裸类型

---

## 配置说明

### Gradle版本
```kotlin
// build.gradle.kts (根目录)
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
}
```

### SDK版本
```kotlin
// app/build.gradle.kts
android {
    compileSdk = 34
    defaultConfig {
        minSdk = 26
        targetSdk = 34
    }
}
```

### 依赖版本
```kotlin
dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}
```

---

## 后续开发建议

### 1. 签名配置（Release构建必需）

在 `~/.gradle/gradle.properties` 或 `local.properties` 添加：

```properties
RELEASE_STORE_FILE=/path/to/keystore.jks
RELEASE_STORE_PASSWORD=your_store_password
RELEASE_KEY_ALIAS=your_key_alias
RELEASE_KEY_PASSWORD=your_key_password
```

### 2. 多渠道打包

在 `app/build.gradle.kts` 添加：

```kotlin
productFlavors {
    create("huawei") { applicationIdSuffix = ".huawei" }
    create("xiaomi") { applicationIdSuffix = ".xiaomi" }
    create("oppo") { applicationIdSuffix = ".oppo" }
}
```

### 3. 性能优化

- 启用R8代码混淆
- 优化资源文件
- 配置ProGuard规则

### 4. 权限管理

当前已配置权限：
- INTERNET
- ACCESS_NETWORK_STATE
- WRITE_EXTERNAL_STORAGE
- READ_EXTERNAL_STORAGE
- REQUEST_INSTALL_PACKAGES
- GET_PACKAGE_SIZE
- FOREGROUND_SERVICE

---

## 常见问题

### Q1: 构建失败，提示插件未找到
**A**: 检查网络连接，或配置Gradle离线模式：
```bash
./gradlew assembleDebug --offline
```

### Q2: Java版本不兼容
**A**: 确保使用JDK 17：
```bash
export JAVA_HOME=/path/to/jdk-17
java -version
```

### Q3: Android SDK未找到
**A**: 设置ANDROID_HOME：
```bash
export ANDROID_HOME=/path/to/android-sdk
echo $ANDROID_HOME
```

### Q4: Gradle同步失败
**A**: 
1. 检查网络
2. 清理缓存：`./gradlew clean`
3. 重新同步

---

## 联系方式

如需技术支持或有任何问题，请参考：
- BUILD_GUIDE.md - 详细构建指南
- PROJECT_STRUCTURE.md - 项目结构文档
- IMPLEMENTATION_PLAN.md - 实现计划

---

## 版本历史

- **v1.0** (2026-05-30)
  - 初始版本
  - 完成基础架构
  - 实现分身创建功能
  - 实现分身管理功能
  - 实现沙盒隔离
  - 实现进程调度

---

**祝您使用愉快！**
