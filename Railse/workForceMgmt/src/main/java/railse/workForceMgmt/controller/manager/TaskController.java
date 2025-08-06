package railse.workForceMgmt.controller.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import railse.workForceMgmt.dto.CreateTaskRequest;
import railse.workForceMgmt.dto.TaskDto;
import railse.workForceMgmt.model.Task;
import railse.workForceMgmt.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/tasks")
public class TaskController
{
    private static final Logger log = LogManager.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public Task createTask(@RequestBody CreateTaskRequest request)
    {
        log.info("createTask method is called");
        return taskService.createTask(request);
    }

    @GetMapping("/range")
    public List<TaskDto> getTasksByRange(@RequestParam String from, @RequestParam String to)
    {
        log.info("getTasksByRange method is called");
        return taskService.getTasksByDateRange(LocalDate.parse(from), LocalDate.parse(to));
    }

    @PostMapping("/reassign/{taskId}")
    public Task reassignTask(@PathVariable UUID taskId, @RequestParam String employeeName, @RequestParam String employeeId,@RequestParam LocalDate startDate,@RequestParam LocalDate dueDate)
    {
        log.info("reassignTask method is called");
        return taskService.reassignTask(taskId, employeeName,employeeId,startDate,dueDate);
    }

    @PutMapping("/priority/{taskId}")
    public void updatePriority(@PathVariable UUID taskId, @RequestParam String priority)
    {
        log.info("updatePriority method is called");
        taskService.updatePriority(taskId, priority);
    }

    @GetMapping("/priority/{priority}")
    public List<TaskDto> getByPriority(@PathVariable String priority)
    {
        log.info("getByPriority method is called");
        return taskService.getTasksByPriority(priority);
    }

    @GetMapping("/{taskId}")
    public TaskDto getDetails(@PathVariable UUID taskId)
    {
        log.info("getDetails method is called");
        return taskService.getTaskDetails(taskId);
    }

    @GetMapping("/")
    public List<TaskDto> showAllTasks()
    {
        log.info("showAllTasks method is called");
        return taskService.getAllTaskDetails();
    }
}