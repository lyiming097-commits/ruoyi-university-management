package com.ruoyi.system.education.service;

import java.util.List;
import java.util.Map;

public interface EducationService
{
    List<Map<String, Object>> colleges(Map<String, Object> query);
    Map<String, Object> college(Long id);
    int addCollege(Map<String, Object> row);
    int editCollege(Map<String, Object> row);
    int removeColleges(Long[] ids);
    List<Map<String, Object>> majors(Map<String, Object> query);
    Map<String, Object> major(Long id);
    int addMajor(Map<String, Object> row);
    int editMajor(Map<String, Object> row);
    int removeMajors(Long[] ids);
    List<Map<String, Object>> classes(Map<String, Object> query);
    Map<String, Object> clazz(Long id);
    int addClass(Map<String, Object> row);
    int editClass(Map<String, Object> row);
    int removeClasses(Long[] ids);
    List<Map<String, Object>> students(Map<String, Object> query);
    Map<String, Object> student(Long id);
    int addStudent(Map<String, Object> row);
    int editStudent(Map<String, Object> row);
    int removeStudents(Long[] ids);
    List<Map<String, Object>> teachers(Map<String, Object> query);
    Map<String, Object> teacher(Long id);
    int addTeacher(Map<String, Object> row);
    int editTeacher(Map<String, Object> row);
    int removeTeachers(Long[] ids);

    List<Map<String, Object>> courses(Map<String, Object> query);
    Map<String, Object> course(Long id);
    int addCourse(Map<String, Object> row);
    int editCourse(Map<String, Object> row);
    int removeCourses(Long[] ids);

    List<Map<String, Object>> classrooms(Map<String, Object> query);
    Map<String, Object> classroom(Long id);
    int addClassroom(Map<String, Object> row);
    int editClassroom(Map<String, Object> row);
    int removeClassrooms(Long[] ids);

    List<Map<String, Object>> teachingClasses(Map<String, Object> query);
    Map<String, Object> teachingClass(Long id);
    int addTeachingClass(Map<String, Object> row);
    int editTeachingClass(Map<String, Object> row);
    int removeTeachingClasses(Long[] ids);

    List<Map<String, Object>> schedules(Map<String, Object> query);
    Map<String, Object> schedule(Long id);
    int saveSchedule(Map<String, Object> row);
    int removeSchedules(Long[] ids);

    List<Map<String, Object>> batches(Map<String, Object> query);
    Map<String, Object> batch(Long id);
    int addBatch(Map<String, Object> row);
    int editBatch(Map<String, Object> row);

    int selectCourse(Long studentId, Long teachingClassId, Long batchId);
    int dropCourse(Long studentId, Long selectionId);
    List<Map<String, Object>> mySelections(Long studentId);
    List<Map<String, Object>> selections(Map<String, Object> query);
}
