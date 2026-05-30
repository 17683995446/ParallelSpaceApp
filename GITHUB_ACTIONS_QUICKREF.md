# 🎯 GitHub Actions APK 构建 - 快速参考卡

## ⚡ 30秒上手

```
推送到 GitHub → 等待 5-10分钟 → 下载 APK
```

---

## 📋 操作清单

### ✅ 第1步：推送代码（本地执行）
```bash
git add .
git commit -m "Initial commit"
git remote add origin https://github.com/你的用户名/ParallelSpaceApp.git
git push -u origin main
```

### ✅ 第2步：查看构建（浏览器操作）
1. 打开：https://github.com/你的用户名/ParallelSpaceApp
2. 点击 **Actions** 标签
3. 看到绿色进度条 ✅
4. 等待显示 ✓ Success

### ✅ 第3步：下载 APK（浏览器操作）
1. 点击构建任务（绿色 ✓）
2. 向下滚动到 **Artifacts** 部分
3. 点击 **app-debug-apk**
4. 自动下载 ZIP 文件
5. 解压获取 APK

---

## 🎯 关键信息

| 项目 | 值 |
|------|-----|
| 构建时间 | 5-10 分钟 |
| 构建触发 | push / PR / 手动 |
| APK 位置 | Artifacts / app-debug-apk |
| 保留时间 | 30 天 |
| 免费额度 | 每月 2000 分钟 |

---

## 💡 常见问题

### Q: 推送后没看到 Actions？
**A**: 
- 确认 `.github/workflows/android-build.yml` 存在
- 刷新页面
- 检查仓库 Settings → Actions → 是否启用

### Q: 构建失败了？
**A**:
- 点击失败的构建任务
- 查看红色错误日志
- 常见错误：代码语法问题

### Q: 找不到 APK 下载？
**A**:
- 确认构建成功（绿色 ✓）
- 向下滚动到 Artifacts
- 点击文件名下载

### Q: 可以构建 Release 版本吗？
**A**:
- 可以！推送到 main 分支自动构建
- Release APK 在 app-release-apk artifact

---

## 🔧 GitHub Actions 工作流配置

**文件位置**: `.github/workflows/android-build.yml`

**触发条件**:
- ✅ 推送到 main/master 分支 → 构建 Debug + Release
- ✅ 创建 PR → 构建 Debug
- ✅ 手动触发 → 构建 Debug

**构建产物**:
- `app/build/outputs/apk/debug/app-debug.apk`
- `app/build/outputs/apk/release/app-release.apk` (仅 main 分支)

---

## 📦 交付包含

### 1️⃣ GitHub Actions 配置
- `.github/workflows/android-build.yml` - 自动构建工作流
- `push_to_github.sh` - 推送向导脚本

### 2️⃣ 源代码
- 完整的 Android 项目结构
- 25+ Kotlin 源文件
- 4 个模块（严格二叉树架构）

### 3️⃣ 文档
- `GITHUB_ACTIONS_GUIDE.md` - 详细指南
- `README.md` - 快速开始

---

## 🎊 成功标志

### 构建成功 🎉
```
✓ Build Debug APK
✓ Upload APK
```

### 下载成功 ✅
```
文件名: app-debug-apk.zip
大小: 约 1-3 MB
```

---

## 🚀 立即行动

### 5 分钟内完成！

1. **创建 GitHub 仓库**（2分钟）
   - https://github.com/new
   - 仓库名称：ParallelSpaceApp
   - 设为 Public

2. **推送代码**（1分钟）
   ```bash
   git remote add origin https://github.com/你的用户名/ParallelSpaceApp.git
   git push -u origin main
   ```

3. **等待构建**（5-10分钟）
   - 刷新 GitHub Actions 页面
   - 等待绿色 ✓

4. **下载 APK**（1分钟）
   - 点击构建任务
   - 下载 app-debug-apk

**总计：不到 10 分钟，APK 到手！** 🎉

---

## 📞 获取帮助

### 详细文档
- **完整指南**: `GITHUB_ACTIONS_GUIDE.md`
- **快速参考**: 本文件
- **项目说明**: `README.md`

### 遇到问题？
1. 查看构建日志（红色错误信息）
2. 参考 `GITHUB_ACTIONS_GUIDE.md` 故障排除
3. 检查代码是否有语法错误

---

## 🎯 额外功能

### 添加构建徽章
在 `README.md` 添加：
```markdown
[![Build Status](https://github.com/你的用户名/ParallelSpaceApp/actions/workflows/android-build.yml/badge.svg)](https://github.com/你的用户名/ParallelSpaceApp/actions)
```

### 自动 Release
推送到 main 分支自动创建 Release：
- 自动构建 Release APK
- 生成 Release Notes
- 一键发布

---

## ✅ 检查清单

- [ ] GitHub 仓库已创建
- [ ] 代码已推送
- [ ] Actions 显示运行中（黄色 🟡）
- [ ] 构建成功（绿色 ✓）
- [ ] APK 已下载
- [ ] APK 可安装到手机

---

**🎊 恭喜！您已掌握 GitHub Actions 自动构建！**

---

*如有任何问题，请参考 `GITHUB_ACTIONS_GUIDE.md` 详细文档*
