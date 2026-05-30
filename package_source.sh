#!/bin/bash

# 安卓分身APP源代码打包脚本

PROJECT_NAME="ParallelSpaceApp"
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
OUTPUT_FILE="${PROJECT_NAME}_source_${TIMESTAMP}.tar.gz"

echo "========================================"
echo " 安卓分身APP源代码打包工具"
echo "========================================"
echo ""
echo "正在打包项目源代码..."
echo ""

# 创建临时目录用于打包
TEMP_DIR="/tmp/${PROJECT_NAME}_packaging"
rm -rf "$TEMP_DIR"
mkdir -p "$TEMP_DIR"

# 复制项目文件
cp -r /workspace "$TEMP_DIR/${PROJECT_NAME}"

# 清理不需要的文件
find "$TEMP_DIR/${PROJECT_NAME}" -name "build" -type d -exec rm -rf {} + 2>/dev/null
find "$TEMP_DIR/${PROJECT_NAME}" -name ".gradle" -type d -exec rm -rf {} + 2>/dev/null
find "$TEMP_DIR/${PROJECT_NAME}" -name "*.apk" -type f -delete 2>/dev/null
find "$TEMP_DIR/${PROJECT_NAME}" -name "local.properties" -type f -delete 2>/dev/null

# 打包
cd "$TEMP_DIR"
tar -czf "/workspace/$OUTPUT_FILE" "${PROJECT_NAME}"

# 清理临时目录
rm -rf "$TEMP_DIR"

# 生成SHA256校验和
cd /workspace
sha256sum "$OUTPUT_FILE" > "${OUTPUT_FILE}.sha256"

echo "打包完成！"
echo ""
echo "输出文件: $OUTPUT_FILE"
echo "校验文件: ${OUTPUT_FILE}.sha256"
echo ""
echo "SHA256校验和:"
cat "${OUTPUT_FILE}.sha256"
echo ""
echo "========================================"
echo " 使用说明"
echo "========================================"
echo ""
echo "1. 解压源代码:"
echo "   tar -xzf $OUTPUT_FILE"
echo ""
echo "2. 配置Android SDK路径:"
echo "   echo 'sdk.dir=/path/to/android-sdk' > local.properties"
echo ""
echo "3. 使用Gradle Wrapper构建:"
echo "   chmod +x gradlew"
echo "   ./gradlew assembleDebug"
echo ""
echo "4. APK输出位置:"
echo "   app/build/outputs/apk/debug/app-debug.apk"
echo ""
echo "========================================"
echo ""
