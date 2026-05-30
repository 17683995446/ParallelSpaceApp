#!/bin/bash

# 安卓分身APP一键构建脚本

set -e

echo "╔════════════════════════════════════════════════════════════╗"
echo "║     安卓分身APP - ParallelSpaceApp 一键构建工具          ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# 检查Java环境
echo "📋 步骤1：检查Java环境..."
if ! command -v java &> /dev/null; then
    echo "❌ 错误：未找到Java。请安装JDK 17或更高版本。"
    echo "   下载地址：https://adoptium.net/"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n1 | cut -d'"' -f2 | cut -d'.' -f1-2)
echo "✅ Java版本: $JAVA_VERSION"

if [[ "$JAVA_VERSION" < "17" ]]; then
    echo "⚠️  警告：检测到Java $JAVA_VERSION，推荐使用Java 17或更高版本"
fi

# 检查Gradle
echo ""
echo "📋 步骤2：检查Gradle..."
if ! command -v gradle &> /dev/null; then
    echo "⚠️  未找到系统Gradle，将使用Gradle Wrapper"
    USE_WRAPPER=true
else
    GRADLE_VERSION=$(gradle --version | head -n1 | cut -d' ' -f2)
    echo "✅ 系统Gradle版本: $GRADLE_VERSION"
fi

# 检查Android SDK
echo ""
echo "📋 步骤3：检查Android SDK..."

if [ -z "$ANDROID_HOME" ]; then
    if [ -d "$HOME/Android/Sdk" ]; then
        export ANDROID_HOME="$HOME/Android/Sdk"
    elif [ -d "$HOME/Library/Android/sdk" ]; then
        export ANDROID_HOME="$HOME/Library/Android/sdk"
    elif [ -d "/usr/local/android-sdk" ]; then
        export ANDROID_HOME="/usr/local/android-sdk"
    fi
fi

if [ -z "$ANDROID_HOME" ] || [ ! -d "$ANDROID_HOME" ]; then
    echo "⚠️  未找到Android SDK"
    echo "   请设置ANDROID_HOME环境变量："
    echo "   export ANDROID_HOME=/path/to/your/android-sdk"
    echo ""
    echo "   或者创建local.properties文件："
    echo "   echo 'sdk.dir=/path/to/your/android-sdk' > local.properties"
    echo ""
    read -p "是否继续尝试构建？(y/N) " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        exit 1
    fi
else
    echo "✅ Android SDK: $ANDROID_HOME"
    
    if [ ! -f "local.properties" ]; then
        echo "📝 创建local.properties文件..."
        echo "sdk.dir=$ANDROID_HOME" > local.properties
    fi
fi

# 设置Gradle Wrapper权限
echo ""
echo "📋 步骤4：配置Gradle Wrapper..."
if [ -f "./gradlew" ]; then
    chmod +x ./gradlew
    echo "✅ Gradle Wrapper权限已设置"
else
    echo "⚠️  未找到Gradle Wrapper脚本"
    echo "   正在下载Gradle Wrapper..."
    if command -v gradle &> /dev/null; then
        gradle wrapper --gradle-version 8.2
        chmod +x ./gradlew
    else
        echo "❌ 错误：无法创建Gradle Wrapper，请安装Gradle或JDK 17"
        exit 1
    fi
fi

# 开始构建
echo ""
echo "╔════════════════════════════════════════════════════════════╗"
echo "║                    开始构建APK                           ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

if [ -f "./gradlew" ]; then
    ./gradlew assembleDebug --no-daemon
else
    gradle assembleDebug --no-daemon
fi

# 检查构建结果
echo ""
echo "📋 步骤5：检查构建结果..."
if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
    APK_SIZE=$(ls -lh app/build/outputs/apk/debug/app-debug.apk | awk '{print $5}')
    echo ""
    echo "╔════════════════════════════════════════════════════════════╗"
    echo "║              ✅ 构建成功！                               ║"
    echo "╚════════════════════════════════════════════════════════════╝"
    echo ""
    echo "📦 APK文件位置："
    echo "   $(pwd)/app/build/outputs/apk/debug/app-debug.apk"
    echo "   文件大小：$APK_SIZE"
    echo ""
    echo "💡 安装到设备："
    echo "   adb install app/build/outputs/apk/debug/app-debug.apk"
    echo ""
else
    echo "❌ 构建失败，请检查上方错误信息"
    exit 1
fi
