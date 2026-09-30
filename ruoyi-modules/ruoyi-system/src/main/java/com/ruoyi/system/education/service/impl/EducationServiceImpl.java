package com.ruoyi.system.education.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.system.education.mapper.EducationMapper;
import com.ruoyi.system.education.service.EducationService;

@Service
public class EducationServiceImpl implements EducationService
{
    private final EducationMapper mapper;

    public EducationServiceImpl(EducationMapper mapper)
    {
        this.mapper = mapper;
    }

    private Long resolveStudentId(Long userId)
    {
        Long studentId = mapper.selectStudentIdByUserId(userId);
        if (studentId == null) throw new ServiceException("当前账号未绑定学生档案");
        return studentId;
    }

    @Override public List<Map<String, Object>> colleges(Map<String, Object> query) { return mapper.selectColleges(query); }
    @Override public Map<String, Object> college(Long id) { return mapper.selectCollege(id); }
    @Override public int addCollege(Map<String, Object> row) { return mapper.insertCollege(row); }
    @Override public int editCollege(Map<String, Object> row) { return mapper.updateCollege(row); }
    @Override public int removeColleges(Long[] ids) { return mapper.deleteColleges(ids); }
    @Override public List<Map<String, Object>> majors(Map<String, Object> query) { return mapper.selectMajors(query); }
    @Override public Map<String, Object> major(Long id) { return mapper.selectMajor(id); }
    @Override public int addMajor(Map<String, Object> row) { return mapper.insertMajor(row); }
    @Override public int editMajor(Map<String, Object> row) { return mapper.updateMajor(row); }
    @Override public int removeMajors(Long[] ids) { return mapper.deleteMajors(ids); }
    @Override public List<Map<String, Object>> classes(Map<String, Object> query) { return mapper.selectClasses(query); }
    @Override public Map<String, Object> clazz(Long id) { return mapper.selectClass(id); }
    @Override public int addClass(Map<String, Object> row) { return mapper.insertClass(row); }
    @Override public int editClass(Map<String, Object> row) { return mapper.updateClass(row); }
    @Override public int removeClasses(Long[] ids) { return mapper.deleteClasses(ids); }
    @Override public List<Map<String, Object>> students(Map<String, Object> query) { return mapper.selectStudents(query); }
    @Override public Map<String, Object> student(Long id) { return mapper.selectStudent(id); }
    @Override public int addStudent(Map<String, Object> row) { return mapper.insertStudent(row); }
    @Override public int editStudent(Map<String, Object> row) { return mapper.updateStudent(row); }
    @Override public int removeStudents(Long[] ids) { return mapper.deleteStudents(ids); }
    @Override public List<Map<String, Object>> teachers(Map<String, Object> query) { return mapper.selectTeachers(query); }
    @Override public Map<String, Object> teacher(Long id) { return mapper.selectTeacher(id); }
    @Override public int addTeacher(Map<String, Object> row) { return mapper.insertTeacher(row); }
    @Override public int editTeacher(Map<String, Object> row) { return mapper.updateTeacher(row); }
    @Override public int removeTeachers(Long[] ids) { return mapper.deleteTeachers(ids); }

    @Override public List<Map<String, Object>> courses(Map<String, Object> query) { return mapper.selectCourses(query); }
    @Override public Map<String, Object> course(Long id) { return mapper.selectCourse(id); }
    @Override public int addCourse(Map<String, Object> row) { return mapper.insertCourse(row); }
    @Override public int editCourse(Map<String, Object> row) { return mapper.updateCourse(row); }
    @Override public int removeCourses(Long[] ids) { return mapper.deleteCourses(ids); }

    @Override public List<Map<String, Object>> classrooms(Map<String, Object> query) { return mapper.selectClassrooms(query); }
    @Override public Map<String, Object> classroom(Long id) { return mapper.selectClassroom(id); }
    @Override public int addClassroom(Map<String, Object> row) { return mapper.insertClassroom(row); }
    @Override public int editClassroom(Map<String, Object> row) { return mapper.updateClassroom(row); }
    @Override public int removeClassrooms(Long[] ids) { return mapper.deleteClassrooms(ids); }

    @Override public List<Map<String, Object>> teachingClasses(Map<String, Object> query) { return mapper.selectTeachingClasses(query); }
    @Override public Map<String, Object> teachingClass(Long id) { return mapper.selectTeachingClass(id); }
    @Override public int addTeachingClass(Map<String, Object> row) { return mapper.insertTeachingClass(row); }
    @Override public int editTeachingClass(Map<String, Object> row) { return mapper.updateTeachingClass(row); }
    @Override public int removeTeachingClasses(Long[] ids) { return mapper.deleteTeachingClasses(ids); }

    @Override public List<Map<String, Object>> schedules(Map<String, Object> query) { return mapper.selectSchedules(query); }
    @Override public Map<String, Object> schedule(Long id) { return mapper.selectSchedule(id); }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveSchedule(Map<String, Object> row)
    {
        Map<String, Object> conflict = mapper.selectScheduleConflict(row);
        if (conflict != null && !conflict.isEmpty())
        {
            throw new ServiceException("排课冲突：教师、教室或班级在同一时间已有安排");
        }
        Object id = row.get("scheduleId");
        return id == null ? mapper.insertSchedule(row) : mapper.updateSchedule(row);
    }

    @Override public int removeSchedules(Long[] ids) { return mapper.deleteSchedules(ids); }
    @Override public List<Map<String, Object>> batches(Map<String, Object> query) { return mapper.selectBatches(query); }
    @Override public Map<String, Object> batch(Long id) { return mapper.selectBatch(id); }
    @Override public int addBatch(Map<String, Object> row) { return mapper.insertBatch(row); }
    @Override public int editBatch(Map<String, Object> row) { return mapper.updateBatch(row); }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int selectCourse(Long studentId, Long teachingClassId, Long batchId)
    {
        studentId = resolveStudentId(studentId);
        Map<String, Object> batch = mapper.selectBatch(batchId);
        if (batch == null || !"1".equals(String.valueOf(batch.get("status")))
                || !"1".equals(String.valueOf(batch.get("selection_status"))))
        {
            throw new ServiceException("选课批次未开放");
        }
        Date now = new Date();
        if (batch.get("start_time") instanceof Date && now.before((Date) batch.get("start_time")))
            throw new ServiceException("选课尚未开始");
        if (batch.get("end_time") instanceof Date && now.after((Date) batch.get("end_time")))
            throw new ServiceException("选课已结束");
        Map<String, Object> teachingClass = mapper.selectTeachingClassForUpdate(teachingClassId);
        if (teachingClass == null) throw new ServiceException("教学班不存在");
        Map<String, Object> query = new HashMap<>();
        query.put("studentId", studentId);
        query.put("teachingClassId", teachingClassId);
        if (mapper.countStudentSelection(query) > 0) throw new ServiceException("不能重复选择同一教学班");
        query.put("courseId", teachingClass.get("course_id"));
        if (mapper.countStudentScheduleConflict(query) > 0) throw new ServiceException("所选课程与已有课程时间冲突");
        int updated = mapper.incrementSelectedCount(teachingClassId);
        if (updated == 0) throw new ServiceException("课程容量已满");
        Map<String, Object> selection = new HashMap<>();
        selection.put("studentId", studentId);
        selection.put("teachingClassId", teachingClassId);
        selection.put("batchId", batchId);
        selection.put("status", "1");
        selection.put("createTime", new Date());
        return mapper.insertSelection(selection);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int dropCourse(Long studentId, Long selectionId)
    {
        studentId = resolveStudentId(studentId);
        Map<String, Object> query = new HashMap<>();
        query.put("selectionId", selectionId);
        query.put("studentId", studentId);
        Map<String, Object> selection = mapper.selectSelectionForUpdate(query);
        if (selection == null) throw new ServiceException("选课记录不存在或不属于当前学生");
        int result = mapper.deleteSelection(selectionId);
        if (result > 0) mapper.decrementSelectedCount(((Number) selection.get("teaching_class_id")).longValue());
        return result;
    }

    @Override public List<Map<String, Object>> mySelections(Long studentId) { return mapper.selectMySelections(resolveStudentId(studentId)); }
    @Override public List<Map<String, Object>> selections(Map<String, Object> query) { return mapper.selectSelections(query); }
}
