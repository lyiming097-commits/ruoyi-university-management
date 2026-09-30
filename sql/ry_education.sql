-- 高校管理第一期：基础数据、手工排课、选课管理
-- 执行前请先执行官方 ry_20260417.sql。
use `ry-cloud`;

delete from sys_role_menu where menu_id between 120 and 170;
delete from sys_menu where menu_id between 120 and 170;

drop table if exists edu_course_selection;
drop table if exists edu_schedule;
drop table if exists edu_selection_batch;
drop table if exists edu_teaching_class;
drop table if exists edu_classroom;
drop table if exists edu_course;

create table edu_college (
  college_id bigint not null auto_increment, college_code varchar(50) not null, college_name varchar(100) not null, status char(1) default '0',
  create_by varchar(64) default '', create_time datetime, update_by varchar(64) default '', update_time datetime, remark varchar(500) default '',
  primary key(college_id), unique key uk_college_code(college_code)
) engine=innodb comment='学院';
create table edu_major (
  major_id bigint not null auto_increment, college_id bigint not null, major_code varchar(50) not null, major_name varchar(100) not null, status char(1) default '0',
  create_by varchar(64) default '', create_time datetime, update_by varchar(64) default '', update_time datetime, remark varchar(500) default '',
  primary key(major_id), unique key uk_major_code(major_code), key idx_major_college(college_id)
) engine=innodb comment='专业';
create table edu_class (
  class_id bigint not null auto_increment, college_id bigint not null, major_id bigint not null, class_code varchar(50) not null, class_name varchar(100) not null, grade varchar(20) not null, status char(1) default '0',
  create_by varchar(64) default '', create_time datetime, update_by varchar(64) default '', update_time datetime, remark varchar(500) default '',
  primary key(class_id), unique key uk_class_code(class_code), key idx_class_major(major_id)
) engine=innodb comment='行政班';
create table edu_student (
  student_id bigint not null auto_increment, user_id bigint default null, student_no varchar(50) not null, student_name varchar(100) not null, class_id bigint, major_id bigint, status char(1) default '0',
  create_by varchar(64) default '', create_time datetime, update_by varchar(64) default '', update_time datetime, remark varchar(500) default '',
  primary key(student_id), unique key uk_student_no(student_no), key idx_student_class(class_id)
) engine=innodb comment='学生';
create table edu_teacher (
  teacher_id bigint not null auto_increment, user_id bigint default null, teacher_no varchar(50) not null, teacher_name varchar(100) not null, college_id bigint, title varchar(50) default '', status char(1) default '0',
  create_by varchar(64) default '', create_time datetime, update_by varchar(64) default '', update_time datetime, remark varchar(500) default '',
  primary key(teacher_id), unique key uk_teacher_no(teacher_no), key idx_teacher_college(college_id)
) engine=innodb comment='教师';

create table edu_course (
  course_id bigint not null auto_increment comment '课程ID',
  course_code varchar(50) not null comment '课程编码',
  course_name varchar(100) not null comment '课程名称',
  course_type varchar(20) default 'elective' comment '课程类型',
  credits decimal(4,1) default 0 comment '学分',
  hours int default 0 comment '总学时',
  college_name varchar(100) default '' comment '开课学院',
  status char(1) default '0' comment '状态（0正常 1停用）',
  create_by varchar(64) default '', create_time datetime, update_by varchar(64) default '', update_time datetime,
  remark varchar(500) default '', primary key(course_id), unique key uk_course_code(course_code)
) engine=innodb comment='课程基础数据';

create table edu_classroom (
  classroom_id bigint not null auto_increment comment '教室ID',
  building varchar(100) not null comment '教学楼', room_no varchar(30) not null comment '房间号', room_name varchar(100) not null comment '教室名称',
  capacity int not null default 0 comment '容量', room_type varchar(30) default '普通' comment '教室类型', status char(1) default '0',
  create_by varchar(64) default '', create_time datetime, update_by varchar(64) default '', update_time datetime, remark varchar(500) default '',
  primary key(classroom_id), unique key uk_room(building, room_no)
) engine=innodb comment='教室基础数据';

create table edu_teaching_class (
  teaching_class_id bigint not null auto_increment comment '教学班ID', course_id bigint not null, class_name varchar(100) not null,
  teacher_id bigint default null, teacher_name varchar(100) default '', term varchar(30) not null comment '学期', capacity int not null default 0,
  selected_count int not null default 0, status char(1) default '0', create_by varchar(64) default '', create_time datetime, update_by varchar(64) default '', update_time datetime, remark varchar(500) default '',
  primary key(teaching_class_id), key idx_teaching_course(course_id), key idx_teaching_term(term)
) engine=innodb comment='教学班';

create table edu_schedule (
  schedule_id bigint not null auto_increment, teaching_class_id bigint not null, classroom_id bigint not null, term varchar(30) not null,
  week_day tinyint not null comment '星期1-7', start_section tinyint not null, end_section tinyint not null, week_start tinyint default 1, week_end tinyint default 16,
  create_by varchar(64) default '', create_time datetime, update_by varchar(64) default '', update_time datetime, remark varchar(500) default '',
  primary key(schedule_id), key idx_schedule_time(term, week_day, start_section, end_section), key idx_schedule_class(teaching_class_id)
) engine=innodb comment='手工排课记录';

create table edu_selection_batch (
  batch_id bigint not null auto_increment, batch_name varchar(100) not null, term varchar(30) not null, start_time datetime not null, end_time datetime not null,
  selection_status char(1) default '0' comment '选课状态（0关闭 1开放）', status char(1) default '0', create_by varchar(64) default '', create_time datetime, update_by varchar(64) default '', update_time datetime, remark varchar(500) default '',
  primary key(batch_id), key idx_batch_term(term)
) engine=innodb comment='选课批次';

create table edu_course_selection (
  selection_id bigint not null auto_increment, student_id bigint not null, teaching_class_id bigint not null, batch_id bigint not null, status char(1) default '1' comment '1有效 0退选', create_time datetime not null,
  primary key(selection_id), unique key uk_student_teaching(student_id, teaching_class_id), key idx_selection_student(student_id), key idx_selection_batch(batch_id)
) engine=innodb comment='学生选课记录';

insert into sys_menu values(120, '高校教学管理', 1, 10, 'education', 'system/education/index', '', '', 1, 0, 'M', '0', '0', '', 'school', 'admin', sysdate(), '', null, '高校教学管理目录');
insert into sys_menu values(121, '课程管理', 120, 1, 'course', 'system/education/course', '', '', 1, 0, 'C', '0', '0', 'education:course:list', 'documentation', 'admin', sysdate(), '', null, '课程管理');
insert into sys_menu values(122, '教室管理', 120, 2, 'classroom', 'system/education/classroom', '', '', 1, 0, 'C', '0', '0', 'education:classroom:list', 'build', 'admin', sysdate(), '', null, '教室管理');
insert into sys_menu values(123, '教学班管理', 120, 3, 'teachingClass', 'system/education/teaching-class', '', '', 1, 0, 'C', '0', '0', 'education:teachingClass:list', 'peoples', 'admin', sysdate(), '', null, '教学班管理');
insert into sys_menu values(124, '手工排课', 120, 4, 'schedule', 'system/education/schedule', '', '', 1, 0, 'C', '0', '0', 'education:schedule:list', 'time-range', 'admin', sysdate(), '', null, '手工排课');
insert into sys_menu values(125, '选课批次', 120, 5, 'batch', 'system/education/batch', '', '', 1, 0, 'C', '0', '0', 'education:batch:list', 'date', 'admin', sysdate(), '', null, '选课批次');
insert into sys_menu values(126, '选课记录', 120, 6, 'selection', 'system/education/selection', '', '', 1, 0, 'C', '0', '0', 'education:selection:list', 'list', 'admin', sysdate(), '', null, '选课记录');
insert into sys_menu values(134, '学院管理', 120, 7, 'college', 'system/education/college', '', '', 1, 0, 'C', '0', '0', 'education:college:list', 'school', 'admin', sysdate(), '', null, '学院管理');
insert into sys_menu values(135, '专业管理', 120, 8, 'major', 'system/education/major', '', '', 1, 0, 'C', '0', '0', 'education:major:list', 'skill', 'admin', sysdate(), '', null, '专业管理');
insert into sys_menu values(136, '班级管理', 120, 9, 'class', 'system/education/class', '', '', 1, 0, 'C', '0', '0', 'education:class:list', 'tree', 'admin', sysdate(), '', null, '行政班管理');
insert into sys_menu values(137, '学生管理', 120, 10, 'student', 'system/education/student', '', '', 1, 0, 'C', '0', '0', 'education:student:list', 'peoples', 'admin', sysdate(), '', null, '学生管理');
insert into sys_menu values(138, '教师管理', 120, 11, 'teacher', 'system/education/teacher', '', '', 1, 0, 'C', '0', '0', 'education:teacher:list', 'user', 'admin', sysdate(), '', null, '教师管理');
insert into sys_menu values(127, '课程新增', 121, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'education:course:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(128, '课程修改', 121, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'education:course:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(129, '课程删除', 121, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'education:course:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(130, '排课新增', 124, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'education:schedule:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(131, '排课修改', 124, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'education:schedule:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(132, '学生选课', 126, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'education:selection:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(133, '学生退选', 126, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'education:selection:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(139, '教室查询', 122, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'education:classroom:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(140, '教室新增', 122, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'education:classroom:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(141, '教室修改', 122, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'education:classroom:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(142, '教学班查询', 123, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'education:teachingClass:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(143, '教学班新增', 123, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'education:teachingClass:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(144, '教学班修改', 123, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'education:teachingClass:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(145, '选课批次查询', 125, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'education:batch:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(146, '选课批次新增', 125, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'education:batch:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(147, '选课批次修改', 125, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'education:batch:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(148, '学院查询', 134, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'education:college:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(149, '学院新增', 134, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'education:college:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(150, '学院修改', 134, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'education:college:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(151, '学院删除', 134, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'education:college:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(152, '专业查询', 135, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'education:major:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(153, '专业新增', 135, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'education:major:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(154, '专业修改', 135, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'education:major:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(155, '专业删除', 135, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'education:major:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(156, '班级查询', 136, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'education:class:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(157, '班级新增', 136, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'education:class:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(158, '班级修改', 136, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'education:class:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(159, '学生查询', 137, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'education:student:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(160, '学生新增', 137, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'education:student:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(161, '学生修改', 137, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'education:student:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(162, '教师查询', 138, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'education:teacher:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(163, '教师新增', 138, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'education:teacher:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(164, '教师修改', 138, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'education:teacher:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(165, '课程查询', 121, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'education:course:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(166, '教室删除', 122, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'education:classroom:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(167, '教学班删除', 123, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'education:teachingClass:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(168, '排课查询', 124, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'education:schedule:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(169, '排课删除', 124, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'education:schedule:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values(170, '我的选课', 126, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'education:selection:my', '#', 'admin', sysdate(), '', null, '');

insert into sys_role_menu(role_id, menu_id) select 2, menu_id from sys_menu where menu_id between 120 and 170;
