# 🎯 本地构建指南 - ParallelSpaceApp

## ⚠️ GitHub Actions 限制说明

您的 GitHub 账户的 Actions 免费额度已用完或需要付费才能使用。
**不用担心！您可以在本地轻松构建 APK。**

---

## 💻 本地构建（3步曲）

### 第一步：准备环境

#### 选项 A：使用 Android Studio（推荐）

1. 下载 Android Studio: https://developer.android.com/studio
2. 安装并启动
3. SDK 会自动配置

#### 选项 B：使用命令行

```bash
# 安装 JDK 17
# Ubuntu/Debian:
sudo apt install openjdk-17-jdk

# macOS:
brew install openjdk@17

# Windows: 下载安装包
# https://adoptium.net/temurin/releases/?version=17
```

### 第二步：配置 Android SDK

```bash
# 创建 local.properties 文件
# Windows PowerShell:
echo "sdk.dir=C:\Users\YourName\AppData\Local\Android\Sdk" > local.properties

# Linux/macOS:
echo "sdk.dir=~/Android/Sdk" > local.properties
```

常见 SDK 路径：
- **Windows**: `C:\Users\用户名\AppData\Local\Android\Sdk`
- **macOS**: `~/Library/Android/sdk`
- **Linux**: `~/Android/Sdk`

### 第三步：构建 APK

```bash
# 给 Gradle Wrapper 执行权限
chmod +x gradlew

# 构建 Debug 版本
./gradlew assembleDebug

# 或使用一键构建脚本
./build.sh
```

---

## 📦 获取 APK

构建成功后，APK 文件位置：

```
app/build/outputs/apk/debug/app-debug.apk
```

**文件大小**: 约 1-3 MB

---

## 🔧 完整命令参考

### 基本构建命令

```bash
# 克隆项目（如果还没有）
git clone https://github.com/17683995446/ParallelSpaceApp.git
cd ParallelSpaceApp

# 配置 SDK
echo "sdk.dir=~/Android/Sdk" > local.properties

# 构建 Debug APK
./gradlew assembleDebug

# 查看 APK
ls -lh app/build/outputs/apk/debug/app-debug.apk
```

### 高级构建命令

```bash
# 清理并重新构建
./gradlew clean assembleDebug

# 构建 Release APK（需要签名配置）
./gradlew assembleRelease

# 查看构建详细信息
./gradlew assembleDebug --info

# 并行构建（加快速度）
./gradlew assembleDebug --parallel
```

---

## 🚀 快速开始脚本

我已经为您准备了自动化构建脚本：

```bash
# 1. 给脚本执行权限
chmod +x build.sh

# 2. 运行构建脚本
./build.sh
```

脚本会自动：
- 检查 Java 环境
- 检查 Gradle 环境
- 检查 Android SDK
- 执行构建
- 显示结果

---

## 💡 Windows 用户特别说明

### 使用 Git Bash 或 WSL

推荐使用 Git Bash 或 Windows Subsystem for Linux (WSL)：

```bash
# 在 Git Bash 中
./gradlew.bat assembleDebug

# 或在 WSL 中
./gradlew assembleDebug
```

### 使用 CMD 或 PowerShell

```cmd
gradlew.bat assembleDebug
```

---

## 📊 预计构建时间

| 环境 | 首次构建 | 后续构建 |
|------|---------|---------|
| Android Studio | 3-5 分钟 | 1-2 分钟 |
| 命令行（首次） | 5-10 分钟 | 2-3 分钟 |
| 命令行（缓存） | 2-3 分钟 | 30 秒 |

---

## ❌ 常见问题

### 问题 1: Java 未找到

**错误**: `java: command not found`

**解决**:
```bash
# 设置 JAVA_HOME
export JAVA_HOME=/path/to/jdk-17
export PATH=$JAVA_HOME/bin:$PATH

# 验证
java -version
```

### 问题 2: Android SDK 未找到

**错误**: `ANDROID_HOME is not set`

**解决**:
```bash
# 设置 ANDROID_HOME
export ANDROID_HOME=~/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
```

### 问题 3: Gradle 权限错误

**错误**: `Permission denied: ./gradlew`

**解决**:
```bash
chmod +x gradlew
```

### 问题 4: 构建失败

**解决**:
1. 检查网络连接
2. 清理缓存：`./gradlew clean`
3. 重新构建：`./gradlew assembleDebug --refresh-dependencies`

---

## 🎯 构建产物验证

构建成功后，验证 APK：

```bash
# 检查 APK 文件
file app/build/outputs/apk/debug/app-debug.apk

# 查看 APK 信息（需要 aapt）
aapt dump badging app/build/outputs/apk/debug/app-debug.apk | head -10

# 检查 APK 大小
ls -lh app/build/outputs/apk/debug/app-debug.apk
```

---

## 📱 安装到手机

### 方法 1: 使用 ADB

```bash
# 连接手机（开启 USB 调试）
adb install app/build/outputs/apk/debug/app-debug.apk
```

### 方法 2: 直接传输

1. 将 APK 文件传输到手机
2. 在手机上点击安装

### 方法 3: Android Studio

1. 打开 Android Studio
2. 打开项目
3. 点击 Run 按钮

---

## 🔐 Release APK 构建（可选）

Release APK 需要签名配置：

1. 创建签名密钥：
```bash
keytool -genkey -v -keystore my-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-key-alias
```

2. 在 `app/build.gradle.kts` 中配置：
```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("my-release-key.jks")
            storePassword = "your-store-password"
            keyAlias = "my-key-alias"
            keyPassword = "your-key-password"
        }
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

3. 构建 Release APK：
```bash
./gradlew assembleRelease
```

---

## 🎉 成功标志

构建成功时会显示：
```
BUILD SUCCESSFUL in XXXs
```

APK 文件存在：
```
app/build/outputs/apk/debug/app-debug.apk
```

---

## 📞 获取帮助

如果遇到问题：

1. 检查 Java 版本：`java -version`（应为 17）
2. 检查 Gradle 版本：`./gradlew --version`
3. 检查 SDK：`echo $ANDROID_HOME`
4. 查看详细错误：`./gradlew assembleDebug --stacktrace`

---

## ✅ 总结

**无需 GitHub Actions！本地构建同样简单：**

1. 安装 Android Studio（或命令行工具）
2. 配置 SDK 路径
3. 运行 `./gradlew assembleDebug`
4. 获取 APK！

**预计总耗时：10-20 分钟**

---

**构建愉快！🎉**

如有问题，请参考上方故障排除或重新克隆项目。
