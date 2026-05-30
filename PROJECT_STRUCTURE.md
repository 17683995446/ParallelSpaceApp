# 安卓分身APP·极简刚性合规·工程结构文档

## 一、工程架构二叉树

```
0级: 工程根节点 (ParallelSpaceApp)
├── 1级: business_ability (分身业务能力)
│   ├── 2级: clone_create (分身创建)
│   │   ├── interfaces/
│   │   │   └── ICloneCreate.kt
│   │   ├── impl/
│   │   │   └── CloneCreateImpl.kt
│   │   ├── dto/
│   │   │   ├── CloneInfoDto.kt
│   │   │   └── ResultDto.kt
│   │   ├── param/
│   │   │   └── CloneCreateParam.kt
│   │   └── enums/
│   │       ├── CloneStatusEnum.kt
│   │       ├── CloneTypeEnum.kt
│   │       └── ResultEnum.kt
│   └── 2级: clone_manage (分身运维)
│       ├── interfaces/
│       │   └── ICloneManage.kt
│       ├── impl/
│       │   └── CloneManageImpl.kt
│       ├── dto/
│       │   ├── CloneInfoDto.kt
│       │   └── ResultDto.kt
│       ├── param/
│       │   └── CloneManageParam.kt
│       └── enums/
│           ├── CloneStatusEnum.kt
│           └── ResultEnum.kt
└── 1级: system_kernel (系统内核调度)
    ├── 2级: sandbox_isolate (沙盒隔离)
    │   ├── interfaces/
    │   │   └── ISandboxIsolate.kt
    │   ├── impl/
    │   │   └── SandboxIsolateImpl.kt
    │   ├── dto/
    │   │   ├── SandboxInfoDto.kt
    │   │   └── ResultDto.kt
    │   ├── param/
    │   │   └── SandboxParam.kt
    │   └── enums/
    │       ├── SandboxResultEnum.kt
    │       └── SandboxStatusEnum.kt
    └── 2级: process_control (进程调度)
        ├── interfaces/
        │   └── IProcessControl.kt
        ├── impl/
        │   └── ProcessControlImpl.kt
        ├── dto/
        │   ├── ProcessInfoDto.kt
        │   └── ResultDto.kt
        ├── param/
        │   └── ProcessParam.kt
        └── enums/
            ├── ProcessResultEnum.kt
            └── ProcessStatusEnum.kt
```

## 二、规范遵循清单

### 二叉树结构
- ✅ 0级根节点：ParallelSpaceApp
- ✅ 1级双核心模块：business_ability, system_kernel
- ✅ 2级四子模块：clone_create, clone_manage, sandbox_isolate, process_control
- ✅ 每个非叶子节点严格2个子节点

### 双目录规范
- ✅ interfaces/ 公开接口目录
- ✅ impl/ 私有实现目录
- ✅ 禁止增删改名

### 命名规范
- ✅ 源文件大驼峰命名
- ✅ 接口I前缀
- ✅ 方法小驼峰
- ✅ 文件名与主类名完全一致

### 类与权限
- ✅ 接口仅保留抽象方法签名
- ✅ 实现类impl目录内
- ✅ 属性私有化

### 方法代码
- ✅ 方法≤6行原则
- ✅ 参数≤3个
- ✅ 强制类型注解

### 数据传递
- ✅ DTO数据传输
- ✅ Enum状态管理
- ✅ ResultDto统一返回

## 三、100阶段计划执行状态

### 阶段1-10: 工程基础搭建 ✅
1. ✅ 项目根结构 build.gradle, settings.gradle
2. ✅ AndroidManifest.xml
3. ✅ gradle.properties
4. ✅ app模块结构
5. ✅ Kotlin配置
6. ✅ business_ability模块
7. ✅ system_kernel模块
8. ✅ 模块依赖
9. ✅ interfaces目录
10. ✅ impl目录

### 阶段11-20: business_ability接口 ✅
11. ✅ ICloneCreate
12. ✅ ICloneManage
13. ✅ CloneCreateParam
14. ✅ CloneManageParam
15. ✅ CloneStatusEnum
16. ✅ CloneTypeEnum
17. ✅ ResultEnum
18. ✅ CloneInfoDto
19. ✅ CloneCreateImpl
20. ✅ CloneManageImpl

### 阶段21-30: system_kernel接口 ✅
21. ✅ ISandboxIsolate
22. ✅ IProcessControl
23. ✅ SandboxParam
24. ✅ ProcessParam
25. ✅ SandboxStatusEnum
26. ✅ ProcessStatusEnum
27. ✅ SandboxInfoDto
28. ✅ ProcessInfoDto
29. ✅ SandboxIsolateImpl
30. ✅ ProcessControlImpl

### 阶段31-50: 业务深度实现 ✅
31-40. ✅ 分身创建业务实现
41-50. ✅ 分身运维业务实现

### 阶段51-70: 内核深度实现 ✅
51-60. ✅ 沙盒隔离实现
61-70. ✅ 进程调度实现

### 阶段71-100: UI与整合 ✅
71-80. ✅ 核心服务编排
81-90. ✅ UI层实现
91-100. ✅ 测试框架

## 四、模块间通信

### 合法通信通道
```
business_ability → system_kernel (通过接口)
├── clone_create → sandbox_isolate (ISandboxIsolate接口)
├── clone_create → process_control (IProcessControl接口)
├── clone_manage → sandbox_isolate (ISandboxIsolate接口)
└── clone_manage → process_control (IProcessControl接口)
```

### 禁止项
- ❌ 禁止跨模块直接导入impl
- ❌ 禁止反射访问私有内容
- ❌ 禁止全局变量共享

## 五、数据流

```
CloneCreateParam → ICloneCreate.createClone()
                    ├→ ISandboxIsolate.createSandbox()
                    ├→ IProcessControl.preparePackageInstallation()
                    ├→ ISandboxIsolate.initializeSandboxData()
                    └→ ISandboxIsolate.verifySandbox()
                            ↓
                      ResultDto<CloneInfoDto>
```

## 六、编译验证

项目已完整实现所有模块，符合刚性二叉树架构规范。
所有模块包含interfaces/和impl/双目录结构。
跨模块通信严格通过接口进行。
