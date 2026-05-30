#!/bin/bash
# ParallelSpaceApp APK Build Script
# Usage: ./build_apk.sh

set -e

# 1. 检查Java
echo "检查Java环境..."
if ! command -v java &> /dev/null; then
    echo "错误: 未找到Java，请先安装JDK 17"
    exit 1
fi
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" != "17" ]; then
    echo "警告: Java版本可能不匹配，建议使用JDK 17"
fi

# 2. 检查Android SDK
echo "检查Android SDK..."
if [ -z "$ANDROID_HOME" ] && [ -z "$ANDROID_SDK_ROOT" ]; then
    echo "错误: 未设置ANDROID_HOME或ANDROID_SDK_ROOT环境变量"
    exit 1
fi
SDK_DIR="${ANDROID_HOME:-$ANDROID_SDK_ROOT}"
if [ ! -d "$SDK_DIR" ]; then
    echo "错误: Android SDK目录不存在"
    exit 1
fi

# 3. 设置工作目录
cd "$(dirname "$0")"

# 4. 确保gradlew可执行
if [ -f "gradlew" ]; then
    chmod +x gradlew
fi

# 5. 清理并构建
echo "开始构建APK..."
if [ -f "gradlew" ]; then
    ./gradlew clean assembleDebug --no-daemon
else
    if command -v gradle &> /dev/null; then
        gradle clean assembleDebug --no-daemon
    else
        echo "错误: 未找到gradlew或gradle命令"
        exit 1
    fi
fi

# 6. 显示结果
APK_PATH="app/build/outputs/apk/debug/app-debug.apk"
if [ -f "$APK_PATH" ]; then
    echo "✅ 构建成功!"
    echo "APK文件: $(pwd)/$APK_PATH"
    ls -lh "$APK_PATH"
else
    echo "❌ 构建失败，请检查错误信息"
    exit 1
fi
