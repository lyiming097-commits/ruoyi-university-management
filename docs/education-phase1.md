# 高校管理第一期

本期把高校业务接入现有 `ruoyi-system` 服务，网关访问前缀为 `/system/education`。

## 初始化

1. 执行官方 `sql/ry_20260417.sql`。
2. 执行 `sql/ry_education.sql`。
3. 为学生账号在 `edu_student.user_id` 建立与 `sys_user.user_id` 的绑定。
4. 在 Nacos 中沿用现有 `ruoyi-system-dev.yml` 配置，不需要新增服务注册项。

## 第一期开关

选课批次同时满足以下条件时才允许选课：

- `status = '1'`
- `selection_status = '1'`
- 当前时间位于 `start_time` 和 `end_time` 之间

选课事务会锁定教学班，检查重复选课、排课时间冲突和容量，并通过唯一索引和余量更新避免重复占位。退选会同步释放容量。

## 主要接口

| 功能 | 接口 |
| --- | --- |
| 学院、专业、班级、学生、教师 | `/system/education/{college|major|class|student|teacher}` |
| 课程、教室、教学班 | `/system/education/{course|classroom|teaching-class}` |
| 手工排课 | `/system/education/schedule` |
| 选课批次 | `/system/education/batch` |
| 学生选课 | `POST /system/education/selection/{teachingClassId}?batchId=...` |
| 学生退选 | `DELETE /system/education/selection/{selectionId}` |
| 当前学生课表 | `GET /system/education/selection/my` |

前端工程不在当前 RuoYi-Cloud 后端仓库中，菜单 SQL 已预置权限标识，后续接入 `RuoYi-Vue` 时可按菜单路径生成页面。
