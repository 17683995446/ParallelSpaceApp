# ParallelSpaceApp - 安卓分身APP

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

## 📂 模块说明

### business_ability - 业务能力层

- **clone_create**: 分身创建模块
- **clone_manage**: 分身管理模块

### system_kernel - 系统内核层

- **sandbox_isolate**: 沙盒隔离模块
- **process_control**: 进程调度模块

## 🚀 构建说明

### 使用 GitHub Actions 自动构建

本项目已配置 GitHub Actions 工作流，代码推送到 main 分支后会自动构建 APK。

1. 访问 [Actions](https://github.com/17683995446/ParallelSpaceApp/actions) 查看构建状态
2. 构建完成后，点击构建任务
3. 下载 **app-debug-apk** artifact 获取 APK 文件

### 本地构建

```bash
# 克隆项目
git clone https://github.com/17683995446/ParallelSpaceApp.git
cd ParallelSpaceApp

# 配置 Android SDK
echo 'sdk.dir=/path/to/android-sdk' > local.properties

# 构建 Debug APK
./gradlew assembleDebug

# APK 输出位置
# app/build/outputs/apk/debug/app-debug.apk
```

## 📄 许可证

本项目仅供学习参考使用。

## 📧 联系方式

如有问题，请通过 GitHub Issues 反馈。
