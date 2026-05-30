# ParallelSpaceApp - 安卓分身APP

## ⚠️ 重要通知

**GitHub Actions 当前不可用**（账户免费额度已用完）。

**好消息**: 您可以轻松在本地构建 APK，无需任何额外费用！

👉 **查看本地构建指南**: [LOCAL_BUILD_GUIDE.md](LOCAL_BUILD_GUIDE.md)

---

## 📱 项目简介

**ParallelSpaceApp** 是一个基于严格二叉树架构的安卓分身应用，采用模块化设计，实现应用多开功能。

## 🏗️ 架构特点

### 二叉树模块结构

```
Level 0: 根节点
  ↓
Level 1: business_ability (业务能力)
Level 1: system_kernel (系统内核)
  ↓         ↓
Level 2: clone_create   sandbox_isolate
Level 2: clone_manage   process_control
```

### 严格规范遵循

- ✅ 二叉树结构（每节点严格2子节点）
- ✅ interfaces + impl 双目录
- ✅ 大驼峰源文件命名
- ✅ I前缀接口命名
- ✅ DTO/Enum/Param数据传输
- ✅ 编译级私有实现

## 🔧 技术栈

- **语言**: Kotlin
- **架构**: 二叉树模块化
- **最低SDK**: Android 8.0 (API 26)
- **目标SDK**: Android 14 (API 34)
- **构建工具**: Gradle 8.2

## 📦 快速获取 APK

### 方法 1: Android Studio（推荐 ⭐）

1. 下载 Android Studio: https://developer.android.com/studio
2. 打开项目：File → Open → 选择项目目录
3. 构建 APK：Build → Build APK
4. 获取 APK：点击 locate 查看位置

**预计时间**: 20 分钟

### 方法 2: 命令行

```bash
# 克隆项目
git clone https://github.com/17683995446/ParallelSpaceApp.git
cd ParallelSpaceApp

# 配置 SDK 路径
echo "sdk.dir=~/Android/Sdk" > local.properties

# 构建
./gradlew assembleDebug

# APK 位置
app/build/outputs/apk/debug/app-debug.apk
```

详细指南：[LOCAL_BUILD_GUIDE.md](LOCAL_BUILD_GUIDE.md)

## 🚀 备选方案

### 升级 GitHub 计划
- $4/月 GitHub Pro
- 无限 Actions 分钟
- 私有仓库无限存储

### 使用其他 CI/CD
- GitLab CI（每月 2000 分钟免费）
- Travis CI（开源项目免费）

## 📂 模块说明

### business_ability - 业务能力层

- **clone_create**: 分身创建模块
- **clone_manage**: 分身管理模块

### system_kernel - 系统内核层

- **sandbox_isolate**: 沙盒隔离模块
- **process_control**: 进程调度模块

## 📄 许可证

本项目仅供学习参考使用。

## 📧 联系方式

GitHub 仓库: https://github.com/17683995446/ParallelSpaceApp

---

**项目代码已完整保存在 GitHub 私有仓库！** 🎉
