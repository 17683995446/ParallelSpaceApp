#!/bin/bash

# GitHub 推送脚本 - 自动推送到 GitHub 并触发构建

echo "╔════════════════════════════════════════════════════════════╗"
echo "║       GitHub 推送向导 - ParallelSpaceApp                ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# 检查 git 是否安装
if ! command -v git &> /dev/null; then
    echo "❌ 错误：未安装 Git"
    echo "   请先安装 Git: https://git-scm.com/downloads"
    exit 1
fi

echo "✅ Git 已安装"
echo ""

# 检查是否已是 Git 仓库
if [ ! -d ".git" ]; then
    echo "📁 初始化 Git 仓库..."
    git init
    git add .
    git commit -m "Initial commit: Android Clone App with strict architecture"
    echo "✅ Git 仓库已初始化"
else
    echo "📁 Git 仓库已存在"
fi

echo ""
echo "请选择推送方式："
echo ""
echo "1) 创建新仓库（需要 GitHub 个人访问令牌）"
echo "2) 推送到已有仓库"
echo "3) 仅生成本地仓库，稍后手动推送"
echo ""

read -p "请输入选择 (1/2/3): " choice

case $choice in
    1)
        echo ""
        echo "📝 创建新 GitHub 仓库"
        echo ""
        read -p "请输入 GitHub 用户名: " github_user
        read -p "请输入仓库名称 (默认: ParallelSpaceApp): " repo_name
        repo_name=${repo_name:-ParallelSpaceApp}
        
        echo ""
        echo "⚠️  需要 GitHub 个人访问令牌 (Personal Access Token)"
        echo "   获取方式:"
        echo "   1. 打开 https://github.com/settings/tokens"
        echo "   2. 点击 'Generate new token (classic)'"
        echo "   3. 勾选 'repo' 权限"
        echo "   4. 生成并复制令牌"
        echo ""
        read -s -p "请输入 GitHub 令牌: " github_token
        echo ""
        
        # 创建远程仓库
        echo ""
        echo "🔄 创建 GitHub 仓库..."
        
        # 使用 GitHub API 创建仓库
        curl -s -u "$github_user:$github_token" \
            -d "{\"name\":\"$repo_name\",\"description\":\"Android Clone App with strict binary tree architecture\"}" \
            https://api.github.com/user/repos > /dev/null
        
        # 添加远程仓库
        git remote add origin "https://github.com/$github_user/$repo_name.git"
        git remote set-url origin "https://github.com/$github_user/$repo_name.git"
        
        # 推送代码
        echo ""
        echo "🚀 推送代码到 GitHub..."
        git branch -M main
        git push -u origin main
        
        if [ $? -eq 0 ]; then
            echo ""
            echo "╔════════════════════════════════════════════════════════════╗"
            echo "║              ✅ 推送成功！                              ║"
            echo "╚════════════════════════════════════════════════════════════╝"
            echo ""
            echo "📋 下一步操作："
            echo "   1. 打开 https://github.com/$github_user/$repo_name"
            echo "   2. 点击 'Actions' 标签查看构建进度"
            echo "   3. 构建完成后，点击构建任务"
            echo "   4. 下载 'app-debug-apk' 获取 APK 文件"
            echo ""
            echo "⏱️  预计构建时间：5-10分钟"
            echo ""
        else
            echo "❌ 推送失败，请检查令牌权限"
        fi
        ;;
        
    2)
        echo ""
        echo "📝 推送到已有仓库"
        echo ""
        read -p "请输入远程仓库 URL (例如: https://github.com/user/repo.git): " remote_url
        
        git remote add origin "$remote_url" 2>/dev/null || git remote set-url origin "$remote_url"
        
        echo ""
        echo "🚀 推送代码..."
        git branch -M main
        git push -u origin main
        
        if [ $? -eq 0 ]; then
            echo ""
            echo "✅ 推送成功！"
            echo "   打开 GitHub Actions 查看构建状态"
        fi
        ;;
        
    3)
        echo ""
        echo "📦 本地仓库已准备好"
        echo ""
        echo "💡 稍后推送："
        echo "   git remote add origin <your-github-repo-url>"
        echo "   git push -u origin main"
        echo ""
        ;;
        
    *)
        echo ""
        echo "❌ 无效选择"
        exit 1
        ;;
esac

echo ""
