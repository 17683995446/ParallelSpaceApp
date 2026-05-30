# 安卓分身APP构建指南

## 环境要求

1. **JDK 17+**
2. **Android SDK** (API Level 34)
3. **Gradle 8.2+** (或使用项目自带wrapper)

## 快速构建步骤

### 方式一：使用项目内嵌Gradle Wrapper

```bash
# Linux/Mac
chmod +x gradlew
./gradlew assembleDebug

# Windows
gradlew.bat assembleDebug
```

### 方式二：使用系统Gradle

```bash
gradle assembleDebug
```

## 构建输出

APK文件位置：
```
app/build/outputs/apk/debug/app-debug.apk
```

## 详细构建流程

### 1. 验证环境

```bash
java -version
# 应显示 Java 17+

gradle --version
# 应显示 Gradle 8.2+

echo $ANDROID_HOME
# 应显示 Android SDK 路径
```

### 2. 配置Android SDK

如果未设置ANDROID_HOME：

```bash
# Linux/Mac
export ANDROID_HOME=/path/to/android-sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools

# Windows PowerShell
$env:ANDROID_HOME="C:\path\to\android-sdk"
```

### 3. 接受协议并安装依赖

```bash
# 首次构建前需要接受Android SDK许可
yes | sdkmanager --licenses

# 安装必要的SDK组件
sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
```

### 4. 执行构建

```bash
# 清理并构建Debug版本
./gradlew clean assembleDebug

# 构建Release版本（需要签名配置）
./gradlew assembleRelease
```

### 5. 验证构建结果

```bash
# 检查APK是否存在
ls -lh app/build/outputs/apk/debug/app-debug.apk

# 查看APK信息
aapt dump badging app/build/outputs/apk/debug/app-debug.apk | head -10
```

## 构建变体

### Debug版本
```bash
./gradlew assembleDebug
# 特点：可调试，包含调试符号
# 输出：app/build/outputs/apk/debug/
```

### Release版本
```bash
./gradlew assembleRelease
# 特点：优化、混淆、签名
# 输出：app/build/outputs/apk/release/
# 注意：需要配置签名密钥
```

## 常见问题

### 问题1：Gradle版本不兼容
**解决方案**：更新Gradle wrapper版本
```bash
./gradlew wrapper --gradle-version=8.2
```

### 问题2：Android SDK未找到
**解决方案**：
1. 下载Android Studio或独立SDK Tools
2. 设置ANDROID_HOME环境变量
3. 重新启动终端

### 问题3：依赖下载失败
**解决方案**：
```bash
# 使用国内镜像（可选）
./gradlew assembleDebug --refresh-dependencies
```

### 问题4：Kotlin编译器版本问题
**解决方案**：检查build.gradle.kts中的Kotlin版本配置

## 项目结构

```
ParallelSpaceApp/
├── app/                          # 主应用模块
│   ├── build.gradle.kts
│   └── src/main/
│       ├── java/.../ui/         # UI层
│       ├── res/                  # 资源文件
│       └── AndroidManifest.xml
├── business_ability/             # 业务能力模块
│   ├── clone_create/            # 分身创建
│   └── clone_manage/            # 分身管理
├── system_kernel/               # 系统内核模块
│   ├── sandbox_isolate/         # 沙盒隔离
│   └── process_control/         # 进程控制
├── build.gradle.kts            # 根构建配置
├── settings.gradle.kts         # 模块配置
├── gradle.properties           # Gradle属性
└── gradlew                      # Gradle Wrapper
```

## 后续开发建议

1. **签名配置**：在`app/build.gradle.kts`中添加release签名配置
2. **混淆规则**：完善`app/proguard-rules.pro`
3. **多渠道打包**：配置productFlavors
4. **持续集成**：配置CI/CD自动化构建

## 技术支持

如遇到构建问题，请检查：
1. Java环境
2. Android SDK安装
3. Gradle版本兼容性
4. 网络连接（下载依赖）
