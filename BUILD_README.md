# ParallelSpaceApp APK 构建指南

## 当前状态
✅ 项目代码已完整实现100阶段计划  
✅ GitHub仓库已创建: https://github.com/17683995446/ParallelSpaceApp  
❌ 当前网络环境不稳定，无法下载依赖

## 构建方式

### 方式1: 本地构建（推荐）

#### 前置条件
- JDK 17
- Android SDK (API 34)
- Git

#### 步骤
```bash
# 1. 克隆仓库
git clone https://github.com/17683995446/ParallelSpaceApp.git
cd ParallelSpaceApp

# 2. 配置环境变量
export JAVA_HOME=/path/to/jdk-17
export ANDROID_HOME=/path/to/android-sdk
export PATH=$PATH:$JAVA_HOME/bin:$ANDROID_HOME/cmdline-tools/latest/bin

# 3. 构建（Linux/Mac）
chmod +x build_apk.sh
./build_apk.sh

# 或（Windows）
build_apk.bat

# 或直接使用Gradle
chmod +x gradlew
./gradlew clean assembleDebug --no-daemon
```

#### 产物位置
```
app/build/outputs/apk/debug/app-debug.apk
```

---

### 方式2: GitHub Actions（需要解决账号问题）

项目已配置GitHub Actions工作流: [.github/workflows/android-build.yml](file:///workspace/.github/workflows/android-build.yml)

#### 使用步骤
1. 解决GitHub账号账单问题
2. 推送代码到 `main` 或 `master` 分支
3. 在Actions页面查看构建状态
4. 下载APK artifacts

---

### 方式3: Android Studio

1. 安装Android Studio
2. 打开项目
3. Build -> Build Bundle(s) / APK(s) -> Build APK(s)

---

## 项目结构
```
ParallelSpaceApp/
├── app/                          # 主应用
├── business_ability/            # 业务能力模块
│   ├── clone_create/
│   └── clone_manage/
├── system_kernel/               # 系统内核模块
│   ├── sandbox_isolate/
│   └── process_control/
├── build_apk.sh                 # Linux/Mac构建脚本
├── build_apk.bat                # Windows构建脚本
└── settings.gradle.kts
```

## 技术栈
- Android Gradle Plugin: 7.4.2
- Kotlin: 1.8.20
- compileSdk: 34
- minSdk: 26
- targetSdk: 34
