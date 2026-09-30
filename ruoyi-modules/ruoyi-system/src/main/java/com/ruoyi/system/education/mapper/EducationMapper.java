package com.ruoyi.system.education.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

/**
 * 高校教学管理数据访问层。
 *
 * <p>基础资料使用 Map 是为了让第一期可以直接配合若依代码生成器扩展，稳定字段仍由 SQL 明确维护。</p>
 */
@Mapper
public interface EducationMapper
{
    List<Map<String, Object>> selectColleges(Map<String, Object> query);
    Map<String, Object> selectCollege(Long collegeId);
    int insertCollege(Map<String, Object> row);
    int updateCollege(Map<String, Object> row);
    int deleteColleges(Long[] ids);
    List<Map<String, Object>> selectMajors(Map<String, Object> query);
    Map<String, Object> selectMajor(Long majorId);
    int insertMajor(Map<String, Object> row);
    int updateMajor(Map<String, Object> row);
    int deleteMajors(Long[] ids);
    List<Map<String, Object>> selectClasses(Map<String, Object> query);
    Map<String, Object> selectClass(Long classId);
    int insertClass(Map<String, Object> row);
    int updateClass(Map<String, Object> row);
    int deleteClasses(Long[] ids);
    List<Map<String, Object>> selectStudents(Map<String, Object> query);
    Map<String, Object> selectStudent(Long studentId);
    int insertStudent(Map<String, Object> row);
    int updateStudent(Map<String, Object> row);
    int deleteStudents(Long[] ids);
    Long selectStudentIdByUserId(Long userId);
    List<Map<String, Object>> selectTeachers(Map<String, Object> query);
    Map<String, Object> selectTeacher(Long teacherId);
    int insertTeacher(Map<String, Object> row);
    int updateTeacher(Map<String, Object> row);
    int deleteTeachers(Long[] ids);

    List<Map<String, Object>> selectCourses(Map<String, Object> query);
    Map<String, Object> selectCourse(Long courseId);
    int insertCourse(Map<String, Object> row);
    int updateCourse(Map<String, Object> row);
    int deleteCourses(Long[] ids);

    List<Map<String, Object>> selectClassrooms(Map<String, Object> query);
    Map<String, Object> selectClassroom(Long classroomId);
    int insertClassroom(Map<String, Object> row);
    int updateClassroom(Map<String, Object> row);
    int deleteClassrooms(Long[] ids);

    List<Map<String, Object>> selectTeachingClasses(Map<String, Object> query);
    Map<String, Object> selectTeachingClass(Long teachingClassId);
    int insertTeachingClass(Map<String, Object> row);
    int updateTeachingClass(Map<String, Object> row);
    int deleteTeachingClasses(Long[] ids);

    List<Map<String, Object>> selectSchedules(Map<String, Object> query);
    Map<String, Object> selectSchedule(Long scheduleId);
    Map<String, Object> selectScheduleConflict(Map<String, Object> query);
    int insertSchedule(Map<String, Object> row);
    int updateSchedule(Map<String, Object> row);
    int deleteSchedules(Long[] ids);

    List<Map<String, Object>> selectBatches(Map<String, Object> query);
    Map<String, Object> selectBatch(Long batchId);
    int insertBatch(Map<String, Object> row);
    int updateBatch(Map<String, Object> row);

    Map<String, Object> selectSelectionForUpdate(Map<String, Object> query);
    Map<String, Object> selectTeachingClassForUpdate(Long teachingClassId);
    int countStudentSelection(Map<String, Object> query);
    int countStudentScheduleConflict(Map<String, Object> query);
    int insertSelection(Map<String, Object> row);
    int incrementSelectedCount(Long teachingClassId);
    int decrementSelectedCount(Long teachingClassId);
    int deleteSelection(Long selectionId);
    List<Map<String, Object>> selectMySelections(Long studentId);
    List<Map<String, Object>> selectSelections(Map<String, Object> query);
}
