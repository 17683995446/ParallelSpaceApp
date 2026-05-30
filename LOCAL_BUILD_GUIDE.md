# 🎯 本地构建指南 - ParallelSpaceApp

## ⚠️ GitHub Actions 限制说明

您的 GitHub 账户的 Actions 免费额度已用完或需要付费才能使用。
**不用担心！您可以在本地轻松构建 APK。**

---

## 💻 纯命令行构建（推荐 ⭐）

**不需要安装 Android Studio GUI！** 只需要命令行工具即可。

### 方案 A：一键安装脚本（最简单）

#### Linux/macOS

```bash
# 给脚本执行权限
chmod +x install_android_sdk.sh

# 运行安装脚本
./install_android_sdk.sh

# 安装完成后，构建 APK
./gradlew assembleDebug
```

#### Windows PowerShell

```powershell
# 右键点击 install_android_sdk.ps1
# 选择"使用 PowerShell 运行"
# 或在 PowerShell 中运行：
.\install_android_sdk.ps1

# 安装完成后，构建 APK
.\gradlew.bat assembleDebug
```

---

### 方案 B：手动安装

#### 第一步：安装 JDK 17

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

**macOS:**
```bash
brew install openjdk@17
```

**Windows:**
- 下载: https://adoptium.net/temurin/releases/?version=17
- 或使用 winget: `winget install EclipseAdoptium.Temurin.17.JDK`

#### 第二步：安装 Android SDK 命令行工具

**Linux:**
```bash
# 创建目录
mkdir -p ~/android-sdk/cmdline-tools
cd ~/android-sdk/cmdline-tools

# 下载并解压
curl -O https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip
unzip commandlinetools-linux-11076708_latest.zip
mv cmdline-tools latest
rm commandlinetools-linux-11076708_latest.zip
```

**macOS:**
```bash
mkdir -p ~/android-sdk/cmdline-tools
cd ~/android-sdk/cmdline-tools
curl -O https://dl.google.com/android/repository/commandlinetools-mac-11076708_latest.zip
unzip commandlinetools-mac-11076708_latest.zip
mv cmdline-tools latest
rm commandlinetools-mac-11076708_latest.zip
```

**Windows (PowerShell):**
```powershell
mkdir $env:USERPROFILE\android-sdk\cmdline-tools
cd $env:USERPROFILE\android-sdk\cmdline-tools
Invoke-WebRequest -Uri "https://dl.google.com/android/repository/commandlinetools-win-11076708_latest.zip" -OutFile cmdline-tools.zip
Expand-Archive -Path cmdline-tools.zip -DestinationPath .
Move-Item -Path cmdline-tools -Destination latest
Remove-Item cmdline-tools.zip
```

#### 第三步：安装 SDK 组件

```bash
# 设置环境变量
export ANDROID_HOME=~/android-sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools

# Windows PowerShell:
# $env:ANDROID_HOME = "$env:USERPROFILE\android-sdk"
# $env:PATH += ";$env:ANDROID_HOME\cmdline-tools\latest\bin;$env:ANDROID_HOME\platform-tools"

# 接受许可
yes | sdkmanager --licenses

# Windows PowerShell:
# echo y | & "$env:ANDROID_HOME\cmdline-tools\latest\bin\sdkmanager.bat" --licenses

# 安装必需组件
sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
```

#### 第四步：构建项目

```bash
# 进入项目目录
cd ParallelSpaceApp

# 配置 SDK 路径
echo "sdk.dir=$ANDROID_HOME" > local.properties

# Windows PowerShell:
# echo "sdk.dir=$env:ANDROID_HOME" > local.properties

# 构建 Debug APK
./gradlew assembleDebug

# Windows PowerShell:
# .\gradlew.bat assembleDebug
```

---

## 📦 获取 APK

构建成功后，APK 文件位置：

```
app/build/outputs/apk/debug/app-debug.apk
```

**文件大小**: 约 1-3 MB

---

## 🔧 常用命令

```bash
# 构建 Debug APK
./gradlew assembleDebug

# 构建 Release APK
./gradlew assembleRelease

# 清理并重新构建
./gradlew clean assembleDebug

# 查看构建详细信息
./gradlew assembleDebug --info

# 并行构建（加快速度）
./gradlew assembleDebug --parallel

# 跳过测试
./gradlew assembleDebug -x test
```

---

## 📊 预计时间

| 步骤 | Linux/macOS | Windows |
|------|------------|---------|
| JDK 安装 | 5 分钟 | 10 分钟 |
| SDK 下载安装 | 5-10 分钟 | 10-15 分钟 |
| 首次构建 | 5-10 分钟 | 10-15 分钟 |
| **总计** | **15-25 分钟** | **30-40 分钟** |

---

## ❌ 常见问题

### 问题 1: Java 未找到

**错误**: `java: command not found`

**解决**:
```bash
# 确认 Java 已安装
java -version

# 设置 JAVA_HOME
export JAVA_HOME=/path/to/jdk-17
export PATH=$JAVA_HOME/bin:$PATH
```

### 问题 2: Android SDK 未找到

**错误**: `ANDROID_HOME is not set`

**解决**:
```bash
# 设置 ANDROID_HOME
export ANDROID_HOME=~/android-sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools

# 确认设置
echo $ANDROID_HOME
```

### 问题 3: Gradle 权限错误

**错误**: `Permission denied: ./gradlew`

**解决**:
```bash
chmod +x gradlew
```

### 问题 4: 网络下载失败

**解决**:
- 检查网络连接
- 使用代理（如需要）
- 多次重试

### 问题 5: Windows 执行策略错误

**错误**: `cannot be loaded because running scripts is disabled`

**解决** (PowerShell 管理员):
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

---

## 🚀 一键构建脚本

项目已包含自动化脚本：

1. **install_android_sdk.sh** - Linux/macOS SDK 安装脚本
2. **install_android_sdk.ps1** - Windows SDK 安装脚本
3. **build.sh** - 构建脚本

使用方式：
```bash
# Linux/macOS
chmod +x install_android_sdk.sh build.sh
./install_android_sdk.sh
./build.sh

# Windows PowerShell
.\install_android_sdk.ps1
.\build.ps1  # 如果有
```

---

## ✅ 成功标志

构建成功时会显示：
```
BUILD SUCCESSFUL in XXXs
17:41:42: Task execution finished 'assembleDebug'.
```

APK 文件存在：
```
app/build/outputs/apk/debug/app-debug.apk
```

---

## 📱 安装到手机

### 使用 ADB

```bash
# 连接手机（开启 USB 调试）
adb install app/build/outputs/apk/debug/app-debug.apk
```

### 直接传输

1. 将 APK 文件传输到手机
2. 在手机上点击安装

---

## 🎯 总结

**纯命令行构建 APK，无需 Android Studio GUI：**

1. ✅ 运行 `install_android_sdk.sh` (或 `.ps1`)
2. ✅ 运行 `./gradlew assembleDebug`
3. ✅ 获取 APK

**预计总耗时：20-40 分钟**

---

**构建愉快！🎉**

如有问题，请检查常见问题部分或重新运行安装脚本。
