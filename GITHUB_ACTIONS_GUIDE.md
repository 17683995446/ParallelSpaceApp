# GitHub Actions 自动构建指南

## 🎯 概述

GitHub Actions 可以自动构建您的 Android APK，无需在本地配置环境！

---

## ✅ 优势

1. **无需本地环境** - 不需要安装 JDK、Android SDK
2. **自动构建** - 推送到 main 分支自动触发
3. **云端构建** - 使用 GitHub 提供的 ubuntu-latest 虚拟机
4. **版本控制** - 构建历史可追溯
5. **多版本构建** - 同时生成 Debug 和 Release APK

---

## 🚀 使用步骤

### 1. 创建 GitHub 仓库

```bash
# 在 GitHub 上创建新仓库
# 例如: your-username/ParallelSpaceApp

# 添加远程仓库
git remote add origin https://github.com/your-username/ParallelSpaceApp.git
```

### 2. 推送代码到 GitHub

```bash
git add .
git commit -m "Add Android clone app source code"
git branch -M main
git push -u origin main
```

### 3. 查看构建状态

1. 打开 GitHub 仓库
2. 点击 **Actions** 标签页
3. 查看构建进度

### 4. 下载 APK

#### 方式1：从 Artifacts 下载（推荐）

1. 点击构建任务
2. 点击 **app-debug-apk** 或 **app-release-apk**
3. 点击 **Download** 下载 APK

#### 方式2：从 Release 下载

1. 创建新的 GitHub Release
2. APK 会自动附加到 Release

---

## 📊 工作流说明

### 触发条件

| 事件 | 触发条件 | 构建内容 |
|------|---------|---------|
| `push` | 推送到 main/master | Debug + Release APK |
| `pull_request` | 创建PR | Debug APK |
| `workflow_dispatch` | 手动触发 | Debug APK |

### 生成的 APK

```
app/build/outputs/apk/debug/app-debug.apk
app/build/outputs/apk/release/app-release.apk  (仅 main 分支)
```

### Artifact 保留时间
- 默认保留 **30天**

---

## 🔧 自定义配置

### 修改 Java 版本

```yaml
- name: Set up JDK
  uses: actions/setup-java@v4
  with:
    java-version: '17'  # 可改为 '11', '21' 等
```

### 修改 Gradle 任务

```yaml
- name: Build Debug APK
  run: ./gradlew assembleDebug --no-daemon
```

### 添加更多构建变体

```yaml
- name: Build All Variants
  run: ./gradlew assemble
```

---

## 📝 完整操作流程

### 首次设置（5分钟）

```bash
# 1. 克隆本地仓库（如果已经有代码）
git clone https://github.com/your-username/ParallelSpaceApp.git
cd ParallelSpaceApp

# 2. 初始化 Git（如果没有）
git init
git add .
git commit -m "Initial commit"

# 3. 推送到 GitHub
git remote add origin https://github.com/your-username/ParallelSpaceApp.git
git push -u origin main
```

### 后续更新

```bash
# 修改代码后
git add .
git commit -m "Your update message"
git push
# GitHub Actions 会自动构建！
```

---

## ⚠️ 注意事项

### 1. GitHub 免费版限制

- **每月 2000 分钟**构建时间（Ubuntu）
- **Artifact 存储** 500MB 限制
- **Artifact 保留** 最多 30 天

### 2. 敏感信息

**不要**在代码中包含：
- API 密钥
- 签名证书密码
- 个人令牌

**应该**使用 GitHub Secrets：
```yaml
env:
  SIGNING_KEY: ${{ secrets.SIGNING_KEY }}
```

### 3. 构建时间

- 首次构建：约 **5-10分钟**
- 后续构建（缓存）：约 **2-3分钟**

---

## 🎁 附加功能

### 添加构建状态徽章

在 `README.md` 中添加：

```markdown
[![Build Status](https://github.com/your-username/ParallelSpaceApp/actions/workflows/android-build.yml/badge.svg)](https://github.com/your-username/ParallelSpaceApp/actions)
```

### 创建 Release 自动构建

```yaml
- name: Create Release
  if: github.event_name == 'push' && github.ref == 'refs/heads/main'
  uses: actions/create-release@v1
  env:
    GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
  with:
    tag_name: ${{ github.ref }}
    release_name: Release ${{ github.ref }}
    draft: false
    prerelease: false
```

---

## ❓ 常见问题

### Q: 构建失败了怎么办？
**A**: 查看 Actions 日志，常见问题：
- 代码语法错误
- 依赖下载失败（检查网络）
- Gradle 配置错误

### Q: 能构建 Release APK 吗？
**A**: 可以！推送 main 分支会自动构建 Release 版本。

### Q: 如何下载所有 APK？
**A**: 在 Actions 页面，点击构建任务，然后分别下载 Debug 和 Release。

### Q: 能定时构建吗？
**A**: 可以！添加 cron 触发器：
```yaml
on:
  schedule:
    - cron: '0 0 * * 0'  # 每周日凌晨构建
```

---

## 📦 交付流程（推荐）

使用 GitHub Actions，您无需：

❌ 本地安装 JDK  
❌ 本地安装 Android SDK  
❌ 手动运行 Gradle  
❌ 手动下载 APK  

您只需要：

✅ 1. 将代码推送到 GitHub  
✅ 2. 等待自动构建（5-10分钟）  
✅ 3. 下载 APK（从 Artifacts）  

---

## 🎉 开始使用

1. **创建 GitHub 仓库**
2. **推送代码**（包含 `.github/workflows/android-build.yml`）
3. **查看 Actions** 构建状态
4. **下载 APK** 从 Artifacts

---

**简单4步，APK 自动到手！🚀**
