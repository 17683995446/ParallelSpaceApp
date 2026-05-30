@echo off
REM ParallelSpaceApp APK Build Script (Windows)
REM Usage: build_apk.bat

echo 检查Java环境...
where java >nul 2>nul
if errorlevel 1 (
    echo 错误: 未找到Java，请先安装JDK 17
    exit /b 1
)

echo 检查Android SDK...
if not defined ANDROID_HOME (
    if not defined ANDROID_SDK_ROOT (
        echo 错误: 未设置ANDROID_HOME或ANDROID_SDK_ROOT环境变量
        exit /b 1
    )
)

cd /d "%~dp0"

if exist gradlew.bat (
    gradlew.bat clean assembleDebug --no-daemon
) else (
    where gradle >nul 2>nul
    if not errorlevel 1 (
        gradle clean assembleDebug --no-daemon
    ) else (
        echo 错误: 未找到gradlew.bat或gradle命令
        exit /b 1
    )
)

if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo.
    echo ========================================
    echo = 构建成功!
    echo = APK文件: %cd%\app\build\outputs\apk\debug\app-debug.apk
    echo ========================================
    dir "app\build\outputs\apk\debug\app-debug.apk"
) else (
    echo.
    echo ========================================
    echo = 构建失败，请检查错误信息
    echo ========================================
    exit /b 1
)
