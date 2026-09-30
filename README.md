# 高校教学管理微服务系统

基于 [RuoYi-Cloud](https://github.com/yangzongzhuan/RuoYi-Cloud) 构建的高校教学管理系统后端，面向学院、专业、班级、师生、课程、排课和选课等高校教务场景。

当前版本先实现基础数据、手工排课和选课管理，后续可以继续扩展培养方案、成绩管理、考试安排、自动排课、报表和消息通知等业务。

## 当前版本

- RuoYi：3.6.8
- Java：17+
- Spring Boot：4.1.0
- Spring Cloud：2025.1.2
- Spring Cloud Alibaba：2025.1.0.0
- 数据库：MySQL
- 缓存：Redis
- 注册中心与配置中心：Nacos
- 网关：Spring Cloud Gateway
- 权限：Spring Security + Redis + RuoYi RBAC
- 数据访问：MyBatis
- 流量控制：Sentinel
- 分布式事务组件：Seata

## 系统能力

### 基础数据

- 学院管理
- 专业管理
- 行政班管理
- 学生管理
- 教师管理
- 课程管理
- 教室管理
- 教学班管理

### 手工排课

- 维护教学班、教师、教室、学期和上课节次
- 按星期和节次维护排课记录
- 检查教师时间冲突
- 检查教室时间冲突
- 检查教学班时间冲突
- 支持排课记录的新增、修改、查询和删除

### 选课管理

- 维护选课批次和开放时间
- 控制选课批次的开放状态
- 校验学生账号是否绑定学生档案
- 校验重复选课
- 校验课程时间冲突
- 校验教学班容量
- 支持学生选课、退选和查看个人选课记录
- 使用事务、行锁、唯一索引和容量条件更新处理并发选课

## 系统架构

```text
高校管理前端（RuoYi-Vue2 / RuoYi-Vue3）
                    │
                    ▼
          ruoyi-gateway :8080
                    │
       ┌────────────┼────────────┐
       ▼            ▼            ▼
  ruoyi-auth   ruoyi-system   其他业务服务
    :9200         :9201       :9202 / :9203 / :9300
                    │
                    ├── 系统管理
                    ├── 高校教学管理 education
                    └── MyBatis + MySQL

       Nacos：服务注册、服务发现、配置管理
       Redis：登录状态、缓存、权限相关数据
       Sentinel：流量控制
       Seata：分布式事务扩展
```

当前高校业务以 `education` 包接入 `ruoyi-system`，接口通过网关统一使用 `/system/education/**`。业务稳定后，可以再拆分为独立的 `ruoyi-education` 微服务。

## 项目结构

```text
RuoYi-Cloud/
├── ruoyi-gateway/                 网关，端口 8080
├── ruoyi-auth/                    认证中心，端口 9200
├── ruoyi-api/                     服务间调用接口
├── ruoyi-common/                  公共组件
│   ├── ruoyi-common-core          核心工具、统一响应、异常处理
│   ├── ruoyi-common-security      登录用户和权限校验
│   ├── ruoyi-common-redis         Redis 封装
│   ├── ruoyi-common-datascope     数据权限
│   ├── ruoyi-common-datasource    多数据源
│   ├── ruoyi-common-log           操作日志
│   ├── ruoyi-common-seata         分布式事务
│   └── ruoyi-common-swagger       OpenAPI 文档
├── ruoyi-modules/
│   ├── ruoyi-system/              系统管理和高校教学管理，端口 9201
│   │   └── .../com/ruoyi/system/education/
│   │       ├── controller         高校接口层
│   │       ├── service             高校业务层
│   │       └── mapper              MyBatis 数据访问层
│   ├── ruoyi-gen/                 代码生成，端口 9202
│   ├── ruoyi-job/                 定时任务，端口 9203
│   └── ruoyi-file/                文件服务，端口 9300
├── ruoyi-visual/ruoyi-monitor/    服务监控，端口 9100
├── sql/
│   ├── ry_20260417.sql            若依基础库
│   ├── ry_config_20260918.sql     Nacos 配置库
│   └── ry_education.sql           高校业务表和菜单权限
├── docs/education-phase1.md       高校第一期接口和初始化说明
└── pom.xml                        根 Maven 配置
```

## 高校业务数据表

| 表名 | 说明 |
| --- | --- |
| `edu_college` | 学院 |
| `edu_major` | 专业 |
| `edu_class` | 行政班 |
| `edu_student` | 学生及若依用户绑定 |
| `edu_teacher` | 教师 |
| `edu_course` | 课程 |
| `edu_classroom` | 教室 |
| `edu_teaching_class` | 教学班和容量 |
| `edu_schedule` | 手工排课记录 |
| `edu_selection_batch` | 选课批次 |
| `edu_course_selection` | 学生选课记录 |

## 主要接口

所有接口都经过网关，基础路径为 `/system/education`。

| 模块 | 示例接口 |
| --- | --- |
| 学院、专业、班级 | `GET /system/education/college/list` |
| 学生、教师 | `GET /system/education/student/list` |
| 课程、教室 | `GET /system/education/course/list` |
| 教学班 | `GET /system/education/teaching-class/list` |
| 手工排课 | `POST /system/education/schedule` |
| 选课批次 | `POST /system/education/batch` |
| 学生选课 | `POST /system/education/selection/{teachingClassId}?batchId={batchId}` |
| 学生退选 | `DELETE /system/education/selection/{selectionId}` |
| 我的选课 | `GET /system/education/selection/my` |

接口权限使用 RuoYi 权限标识，例如：

```text
education:course:list
education:schedule:add
education:selection:add
education:selection:remove
```

## 本地运行

### 环境要求

- JDK 17 或更高版本
- Maven 3.9+
- MySQL 8+
- Redis 6+
- Nacos 3+
- 可选：Sentinel、Seata

### 初始化数据库

按以下顺序执行：

```text
1. sql/ry_20260417.sql
2. sql/ry_config_20260918.sql
3. sql/ry_education.sql
```

`ry_education.sql` 会创建高校业务表，并初始化高校菜单和管理员角色菜单权限。执行脚本后，需要将学生账号的 `sys_user.user_id` 写入对应的 `edu_student.user_id`，学生才能进行选课。

### 配置服务

默认服务注册和配置中心地址为：

```text
Nacos：127.0.0.1:8848
Redis：127.0.0.1:6379
MySQL：127.0.0.1:3306/ry-cloud
```

数据库用户名、密码和其他环境参数位于 Nacos 配置脚本中，生产环境请替换默认凭据和密钥。

### 编译和启动

```bash
mvn clean package -DskipTests
```

建议启动顺序：

```text
Nacos → MySQL → Redis → ruoyi-auth → ruoyi-system → ruoyi-gateway
```

Windows 下也可以使用 `bin/` 目录中的启动脚本运行已打包的 JAR。

## 开发约定

- 通用用户、角色、菜单、日志能力复用 RuoYi，不重复实现。
- 高校业务代码放在 `com.ruoyi.system.education` 包下。
- Controller 负责参数和响应，业务规则放在 Service。
- 排课冲突和选课容量校验必须在事务中执行。
- 服务之间不要直接读写其他服务的业务表。
- 复杂业务优先增加独立业务模块，稳定后再拆分微服务。

## 后续规划

- RuoYi-Vue 前端页面
- 培养方案和课程要求
- 选课候补队列
- 自动排课和排课方案发布
- 学生课表、教师课表、班级课表
- 成绩管理和考试安排
- 教务统计报表
- 消息通知和导入导出

## 相关文档

- [高校第一期实现说明](docs/education-phase1.md)
- [RuoYi-Cloud 官方仓库](https://github.com/yangzongzhuan/RuoYi-Cloud)
- [本项目 GitHub 仓库](https://github.com/lyiming097-commits/ruoyi-university-management)

## 许可证

本项目基于 RuoYi-Cloud 开发，遵循原项目的开源许可证。使用和二次开发时请保留原项目版权和许可证说明。
