package com.ruoyi.system.education.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.education.service.EducationService;

/** 高校基础数据、手工排课和选课管理。通过 /system/education/** 暴露到网关。 */
@RestController
@RequestMapping("/education")
public class EducationController extends BaseController
{
    private final EducationService service;

    public EducationController(EducationService service) { this.service = service; }

    @GetMapping("/college/list") @RequiresPermissions("education:college:list")
    public TableDataInfo collegeList(@RequestParam Map<String, Object> query) { startPage(); return getDataTable(service.colleges(query)); }
    @GetMapping("/college/{id}") @RequiresPermissions("education:college:query")
    public AjaxResult college(@PathVariable Long id) { return success(service.college(id)); }
    @PostMapping("/college") @RequiresPermissions("education:college:add") public AjaxResult addCollege(@RequestBody Map<String, Object> row) { row.put("createBy", SecurityUtils.getUsername()); return toAjax(service.addCollege(row)); }
    @PutMapping("/college") @RequiresPermissions("education:college:edit") public AjaxResult editCollege(@RequestBody Map<String, Object> row) { return toAjax(service.editCollege(row)); }
    @DeleteMapping("/college/{ids}") @RequiresPermissions("education:college:remove") public AjaxResult removeColleges(@PathVariable Long[] ids) { return toAjax(service.removeColleges(ids)); }

    @GetMapping("/major/list") @RequiresPermissions("education:major:list")
    public TableDataInfo majorList(@RequestParam Map<String, Object> query) { startPage(); return getDataTable(service.majors(query)); }
    @GetMapping("/major/{id}") @RequiresPermissions("education:major:query") public AjaxResult major(@PathVariable Long id) { return success(service.major(id)); }
    @PostMapping("/major") @RequiresPermissions("education:major:add") public AjaxResult addMajor(@RequestBody Map<String, Object> row) { return toAjax(service.addMajor(row)); }
    @PutMapping("/major") @RequiresPermissions("education:major:edit") public AjaxResult editMajor(@RequestBody Map<String, Object> row) { return toAjax(service.editMajor(row)); }
    @DeleteMapping("/major/{ids}") @RequiresPermissions("education:major:remove") public AjaxResult removeMajors(@PathVariable Long[] ids) { return toAjax(service.removeMajors(ids)); }

    @GetMapping("/class/list") @RequiresPermissions("education:class:list")
    public TableDataInfo classList(@RequestParam Map<String, Object> query) { startPage(); return getDataTable(service.classes(query)); }
    @GetMapping("/class/{id}") @RequiresPermissions("education:class:query") public AjaxResult clazz(@PathVariable Long id) { return success(service.clazz(id)); }
    @PostMapping("/class") @RequiresPermissions("education:class:add") public AjaxResult addClass(@RequestBody Map<String, Object> row) { return toAjax(service.addClass(row)); }
    @PutMapping("/class") @RequiresPermissions("education:class:edit") public AjaxResult editClass(@RequestBody Map<String, Object> row) { return toAjax(service.editClass(row)); }
    @DeleteMapping("/class/{ids}") @RequiresPermissions("education:class:remove") public AjaxResult removeClasses(@PathVariable Long[] ids) { return toAjax(service.removeClasses(ids)); }

    @GetMapping("/student/list") @RequiresPermissions("education:student:list")
    public TableDataInfo studentList(@RequestParam Map<String, Object> query) { startPage(); return getDataTable(service.students(query)); }
    @GetMapping("/student/{id}") @RequiresPermissions("education:student:query") public AjaxResult student(@PathVariable Long id) { return success(service.student(id)); }
    @PostMapping("/student") @RequiresPermissions("education:student:add") public AjaxResult addStudent(@RequestBody Map<String, Object> row) { return toAjax(service.addStudent(row)); }
    @PutMapping("/student") @RequiresPermissions("education:student:edit") public AjaxResult editStudent(@RequestBody Map<String, Object> row) { return toAjax(service.editStudent(row)); }
    @DeleteMapping("/student/{ids}") @RequiresPermissions("education:student:remove") public AjaxResult removeStudents(@PathVariable Long[] ids) { return toAjax(service.removeStudents(ids)); }

    @GetMapping("/teacher/list") @RequiresPermissions("education:teacher:list")
    public TableDataInfo teacherList(@RequestParam Map<String, Object> query) { startPage(); return getDataTable(service.teachers(query)); }
    @GetMapping("/teacher/{id}") @RequiresPermissions("education:teacher:query") public AjaxResult teacher(@PathVariable Long id) { return success(service.teacher(id)); }
    @PostMapping("/teacher") @RequiresPermissions("education:teacher:add") public AjaxResult addTeacher(@RequestBody Map<String, Object> row) { return toAjax(service.addTeacher(row)); }
    @PutMapping("/teacher") @RequiresPermissions("education:teacher:edit") public AjaxResult editTeacher(@RequestBody Map<String, Object> row) { return toAjax(service.editTeacher(row)); }
    @DeleteMapping("/teacher/{ids}") @RequiresPermissions("education:teacher:remove") public AjaxResult removeTeachers(@PathVariable Long[] ids) { return toAjax(service.removeTeachers(ids)); }

    @GetMapping("/course/list") @RequiresPermissions("education:course:list")
    public TableDataInfo courseList(@RequestParam Map<String, Object> query) { startPage(); return getDataTable(service.courses(query)); }
    @GetMapping("/course/{id}") @RequiresPermissions("education:course:query")
    public AjaxResult course(@PathVariable Long id) { return success(service.course(id)); }
    @PostMapping("/course") @RequiresPermissions("education:course:add") @Log(title = "课程管理", businessType = BusinessType.INSERT)
    public AjaxResult addCourse(@RequestBody Map<String, Object> row) { row.put("createBy", SecurityUtils.getUsername()); return toAjax(service.addCourse(row)); }
    @PutMapping("/course") @RequiresPermissions("education:course:edit") @Log(title = "课程管理", businessType = BusinessType.UPDATE)
    public AjaxResult editCourse(@RequestBody Map<String, Object> row) { row.put("updateBy", SecurityUtils.getUsername()); return toAjax(service.editCourse(row)); }
    @DeleteMapping("/course/{ids}") @RequiresPermissions("education:course:remove") @Log(title = "课程管理", businessType = BusinessType.DELETE)
    public AjaxResult removeCourses(@PathVariable Long[] ids) { return toAjax(service.removeCourses(ids)); }

    @GetMapping("/classroom/list") @RequiresPermissions("education:classroom:list")
    public TableDataInfo classroomList(@RequestParam Map<String, Object> query) { startPage(); return getDataTable(service.classrooms(query)); }
    @GetMapping("/classroom/{id}") @RequiresPermissions("education:classroom:query")
    public AjaxResult classroom(@PathVariable Long id) { return success(service.classroom(id)); }
    @PostMapping("/classroom") @RequiresPermissions("education:classroom:add") @Log(title = "教室管理", businessType = BusinessType.INSERT)
    public AjaxResult addClassroom(@RequestBody Map<String, Object> row) { row.put("createBy", SecurityUtils.getUsername()); return toAjax(service.addClassroom(row)); }
    @PutMapping("/classroom") @RequiresPermissions("education:classroom:edit") @Log(title = "教室管理", businessType = BusinessType.UPDATE)
    public AjaxResult editClassroom(@RequestBody Map<String, Object> row) { return toAjax(service.editClassroom(row)); }
    @DeleteMapping("/classroom/{ids}") @RequiresPermissions("education:classroom:remove") @Log(title = "教室管理", businessType = BusinessType.DELETE)
    public AjaxResult removeClassrooms(@PathVariable Long[] ids) { return toAjax(service.removeClassrooms(ids)); }

    @GetMapping("/teaching-class/list") @RequiresPermissions("education:teachingClass:list")
    public TableDataInfo teachingClassList(@RequestParam Map<String, Object> query) { startPage(); return getDataTable(service.teachingClasses(query)); }
    @GetMapping("/teaching-class/{id}") @RequiresPermissions("education:teachingClass:query")
    public AjaxResult teachingClass(@PathVariable Long id) { return success(service.teachingClass(id)); }
    @PostMapping("/teaching-class") @RequiresPermissions("education:teachingClass:add") @Log(title = "教学班管理", businessType = BusinessType.INSERT)
    public AjaxResult addTeachingClass(@RequestBody Map<String, Object> row) { row.put("createBy", SecurityUtils.getUsername()); return toAjax(service.addTeachingClass(row)); }
    @PutMapping("/teaching-class") @RequiresPermissions("education:teachingClass:edit") @Log(title = "教学班管理", businessType = BusinessType.UPDATE)
    public AjaxResult editTeachingClass(@RequestBody Map<String, Object> row) { return toAjax(service.editTeachingClass(row)); }
    @DeleteMapping("/teaching-class/{ids}") @RequiresPermissions("education:teachingClass:remove") @Log(title = "教学班管理", businessType = BusinessType.DELETE)
    public AjaxResult removeTeachingClasses(@PathVariable Long[] ids) { return toAjax(service.removeTeachingClasses(ids)); }

    @GetMapping("/schedule/list") @RequiresPermissions("education:schedule:list")
    public TableDataInfo scheduleList(@RequestParam Map<String, Object> query) { startPage(); return getDataTable(service.schedules(query)); }
    @GetMapping("/schedule/{id}") @RequiresPermissions("education:schedule:query")
    public AjaxResult schedule(@PathVariable Long id) { return success(service.schedule(id)); }
    @PostMapping("/schedule") @RequiresPermissions("education:schedule:add") @Log(title = "手工排课", businessType = BusinessType.INSERT)
    public AjaxResult addSchedule(@RequestBody Map<String, Object> row) { row.put("createBy", SecurityUtils.getUsername()); return toAjax(service.saveSchedule(row)); }
    @PutMapping("/schedule") @RequiresPermissions("education:schedule:edit") @Log(title = "手工排课", businessType = BusinessType.UPDATE)
    public AjaxResult editSchedule(@RequestBody Map<String, Object> row) { row.put("updateBy", SecurityUtils.getUsername()); return toAjax(service.saveSchedule(row)); }
    @DeleteMapping("/schedule/{ids}") @RequiresPermissions("education:schedule:remove") @Log(title = "手工排课", businessType = BusinessType.DELETE)
    public AjaxResult removeSchedules(@PathVariable Long[] ids) { return toAjax(service.removeSchedules(ids)); }

    @GetMapping("/batch/list") @RequiresPermissions("education:batch:list")
    public TableDataInfo batchList(@RequestParam Map<String, Object> query) { startPage(); return getDataTable(service.batches(query)); }
    @GetMapping("/batch/{id}") @RequiresPermissions("education:batch:query")
    public AjaxResult batch(@PathVariable Long id) { return success(service.batch(id)); }
    @PostMapping("/batch") @RequiresPermissions("education:batch:add") @Log(title = "选课批次", businessType = BusinessType.INSERT)
    public AjaxResult addBatch(@RequestBody Map<String, Object> row) { row.put("createBy", SecurityUtils.getUsername()); return toAjax(service.addBatch(row)); }
    @PutMapping("/batch") @RequiresPermissions("education:batch:edit") @Log(title = "选课批次", businessType = BusinessType.UPDATE)
    public AjaxResult editBatch(@RequestBody Map<String, Object> row) { return toAjax(service.editBatch(row)); }

    @GetMapping("/selection/list") @RequiresPermissions("education:selection:list")
    public TableDataInfo selectionList(@RequestParam Map<String, Object> query) { startPage(); return getDataTable(service.selections(query)); }
    @GetMapping("/selection/my") @RequiresPermissions("education:selection:my")
    public AjaxResult mySelections() { return success(service.mySelections(SecurityUtils.getUserId())); }
    @PostMapping("/selection/{teachingClassId}") @RequiresPermissions("education:selection:add") @Log(title = "学生选课", businessType = BusinessType.INSERT)
    public AjaxResult select(@PathVariable Long teachingClassId, @RequestParam Long batchId) { return toAjax(service.selectCourse(SecurityUtils.getUserId(), teachingClassId, batchId)); }
    @DeleteMapping("/selection/{selectionId}") @RequiresPermissions("education:selection:remove") @Log(title = "学生退选", businessType = BusinessType.DELETE)
    public AjaxResult drop(@PathVariable Long selectionId) { return toAjax(service.dropCourse(SecurityUtils.getUserId(), selectionId)); }
}
