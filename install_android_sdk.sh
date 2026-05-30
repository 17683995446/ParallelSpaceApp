#!/bin/bash

# Android SDK 命令行一键安装脚本
# 适用于 Linux/macOS

set -e

echo "╔════════════════════════════════════════════════════════════╗"
echo "║   Android SDK 命令行工具一键安装脚本                   ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# 检测操作系统
OS=""
if [[ "$OSTYPE" == "linux-gnu"* ]]; then
    OS="linux"
elif [[ "$OSTYPE" == "darwin"* ]]; then
    OS="mac"
else
    echo "❌ 不支持的操作系统: $OSTYPE"
    echo "   请手动安装或使用 Android Studio"
    exit 1
fi

echo "✅ 检测到操作系统: $OS"
echo ""

# 检查 JDK
echo "📋 检查 Java 环境..."
if ! command -v java &> /dev/null; then
    echo "⚠️  Java 未安装，正在安装..."
    if [ "$OS" == "linux" ]; then
        sudo apt update && sudo apt install -y openjdk-17-jdk
    elif [ "$OS" == "mac" ]; then
        brew install openjdk@17
    fi
fi

JAVA_VERSION=$(java -version 2>&1 | head -n1 | cut -d'"' -f2)
echo "✅ Java 版本: $JAVA_VERSION"
echo ""

# 设置安装路径
ANDROID_SDK_DIR="$HOME/android-sdk"
export ANDROID_HOME="$ANDROID_SDK_DIR"
export PATH="$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools"

# 创建目录
echo "📁 创建目录..."
mkdir -p "$ANDROID_SDK_DIR/cmdline-tools"
cd "$ANDROID_SDK_DIR/cmdline-tools"

# 下载命令行工具
echo "📥 下载 Android SDK 命令行工具..."
if [ "$OS" == "linux" ]; then
    CMDLINE_TOOLS_URL="https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip"
elif [ "$OS" == "mac" ]; then
    CMDLINE_TOOLS_URL="https://dl.google.com/android/repository/commandlinetools-mac-11076708_latest.zip"
fi

if [ ! -d "latest" ]; then
    curl -L -o cmdline-tools.zip "$CMDLINE_TOOLS_URL"
    unzip -q cmdline-tools.zip
    mv cmdline-tools latest
    rm cmdline-tools.zip
fi

echo "✅ 命令行工具已安装"
echo ""

# 接受许可
echo "📝 接受许可协议..."
yes | sdkmanager --licenses > /dev/null 2>&1 || true
echo "✅ 许可已接受"
echo ""

# 安装组件
echo "📦 安装 Android SDK 组件..."
echo "   - platform-tools"
echo "   - platforms;android-34"
echo "   - build-tools;34.0.0"
echo ""

sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0" --sdk_root="$ANDROID_HOME"

echo "✅ SDK 组件安装完成"
echo ""

# 返回项目目录
cd - > /dev/null

# 配置 local.properties
echo "⚙️  配置项目..."
if [ -f "local.properties" ]; then
    echo "   local.properties 已存在，跳过"
else
    echo "sdk.dir=$ANDROID_HOME" > local.properties
    echo "   local.properties 已创建"
fi
echo ""

# 提示信息
echo "╔════════════════════════════════════════════════════════════╗"
echo "║              ✅ 安装完成！                               ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""
echo "📋 下一步："
echo ""
echo "1. 构建 Debug APK："
echo "   ./gradlew assembleDebug"
echo ""
echo "2. APK 输出位置："
echo "   app/build/outputs/apk/debug/app-debug.apk"
echo ""
echo "📌 环境变量（当前会话）："
echo "   export ANDROID_HOME=$ANDROID_HOME"
echo "   export PATH=\$PATH:\$ANDROID_HOME/cmdline-tools/latest/bin:\$ANDROID_HOME/platform-tools"
echo ""
echo "💡 如需永久生效，请添加到 ~/.bashrc 或 ~/.zshrc"
echo ""
