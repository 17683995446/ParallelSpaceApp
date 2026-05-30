# Android SDK 命令行一键安装脚本 (Windows PowerShell)
# 使用方法: 右键点击 -> "使用 PowerShell 运行"

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Android SDK 命令行工具安装脚本" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# 检查管理员权限
$currentPrincipal = New-Object Security.Principal.WindowsPrincipal([Security.Principal.WindowsIdentity]::GetCurrent())
$isAdmin = $currentPrincipal.IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)

if (-not $isAdmin) {
    Write-Host "⚠️  建议使用管理员权限运行以避免安装问题" -ForegroundColor Yellow
    Write-Host ""
}

# 检查 Java
Write-Host "📋 检查 Java 环境..." -ForegroundColor Green
$javaVersion = java -version 2>&1 | Select-Object -First 1

if ($LASTEXITCODE -ne 0) {
    Write-Host "⚠️  Java 未安装或未配置环境变量" -ForegroundColor Yellow
    Write-Host "   请先安装 JDK 17: https://adoptium.net/temurin/releases/?version=17" -ForegroundColor Yellow
    Write-Host "   或运行: winget install EclipseAdoptium.Temurin.17.JDK" -ForegroundColor Yellow
    Write-Host ""
    $continue = Read-Host "是否继续？(y/N)"
    if ($continue -ne "y" -and $continue -ne "Y") {
        exit
    }
} else {
    Write-Host "✅ Java 已安装: $javaVersion" -ForegroundColor Green
}
Write-Host ""

# 设置 SDK 安装路径
$ANDROID_SDK_DIR = "$env:USERPROFILE\android-sdk"
$env:ANDROID_HOME = $ANDROID_SDK_DIR

Write-Host "📁 SDK 安装路径: $ANDROID_SDK_DIR" -ForegroundColor Green

# 创建目录
New-Item -ItemType Directory -Force -Path "$ANDROID_SDK_DIR\cmdline-tools" | Out-Null

# 下载命令行工具
Write-Host "📥 下载 Android SDK 命令行工具..." -ForegroundColor Green
$cmdlineToolsZip = "$ANDROID_SDK_DIR\cmdline-tools.zip"
$cmdlineToolsUrl = "https://dl.google.com/android/repository/commandlinetools-win-11076708_latest.zip"

if (-not (Test-Path "$ANDROID_SDK_DIR\cmdline-tools\latest")) {
    Write-Host "   正在下载... (约 150MB)" -ForegroundColor Gray
    [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
    Invoke-WebRequest -Uri $cmdlineToolsUrl -OutFile $cmdlineToolsZip -UseBasicParsing
    
    Write-Host "   解压中..." -ForegroundColor Gray
    Expand-Archive -Path $cmdlineToolsZip -DestinationPath "$ANDROID_SDK_DIR\cmdline-tools" -Force
    Move-Item -Path "$ANDROID_SDK_DIR\cmdline-tools\cmdline-tools" -Destination "$ANDROID_SDK_DIR\cmdline-tools\latest" -Force
    Remove-Item $cmdlineToolsZip -Force
}

Write-Host "✅ 命令行工具已安装" -ForegroundColor Green
Write-Host ""

# 配置 PATH
$env:PATH = "$env:PATH;$ANDROID_SDK_DIR\cmdline-tools\latest\bin;$ANDROID_SDK_DIR\platform-tools"

# 接受许可
Write-Host "📝 接受许可协议..." -ForegroundColor Green
Write-Host "y" | & "$ANDROID_SDK_DIR\cmdline-tools\latest\bin\sdkmanager.bat" --licenses --sdk_root="$ANDROID_HOME" 2>&1 | Out-Null
Write-Host "✅ 许可已接受" -ForegroundColor Green
Write-Host ""

# 安装组件
Write-Host "📦 安装 Android SDK 组件..." -ForegroundColor Green
Write-Host "   - platform-tools" -ForegroundColor Gray
Write-Host "   - platforms;android-34" -ForegroundColor Gray
Write-Host "   - build-tools;34.0.0" -ForegroundColor Gray
Write-Host ""

& "$ANDROID_SDK_DIR\cmdline-tools\latest\bin\sdkmanager.bat" `
    --sdk_root="$ANDROID_HOME" `
    "platform-tools" `
    "platforms;android-34" `
    "build-tools;34.0.0"

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ SDK 组件安装完成" -ForegroundColor Green
} else {
    Write-Host "❌ SDK 组件安装失败" -ForegroundColor Red
    exit 1
}
Write-Host ""

# 配置项目
Write-Host "⚙️  配置项目..." -ForegroundColor Green

# 进入项目目录（假设脚本在项目根目录）
$projectDir = Split-Path -Parent $MyInvocation.MyCommand.Path

if (Test-Path "$projectDir\ParallelSpaceApp") {
    $projectDir = "$projectDir\ParallelSpaceApp"
}

Set-Location $projectDir

if (Test-Path "local.properties") {
    Write-Host "   local.properties 已存在，跳过" -ForegroundColor Gray
} else {
    "sdk.dir=$ANDROID_HOME" | Out-File -FilePath "local.properties" -Encoding utf8
    Write-Host "   local.properties 已创建" -ForegroundColor Gray
}
Write-Host ""

# 完成
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "          ✅ 安装完成！" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "📋 下一步：" -ForegroundColor Yellow
Write-Host ""
Write-Host "1. 构建 Debug APK：" -ForegroundColor White
Write-Host '   .\gradlew.bat assembleDebug' -ForegroundColor Cyan
Write-Host ""
Write-Host "2. APK 输出位置：" -ForegroundColor White
Write-Host "   app\build\outputs\apk\debug\app-debug.apk" -ForegroundColor Cyan
Write-Host ""
Write-Host "3. 如需在新的 PowerShell 窗口使用 adb 等工具：" -ForegroundColor White
Write-Host "   \$env:ANDROID_HOME = ""$ANDROID_SDK_DIR""" -ForegroundColor Cyan
Write-Host '   $env:PATH += ";$env:ANDROID_HOME\cmdline-tools\latest\bin;$env:ANDROID_HOME\platform-tools"' -ForegroundColor Cyan
Write-Host ""
