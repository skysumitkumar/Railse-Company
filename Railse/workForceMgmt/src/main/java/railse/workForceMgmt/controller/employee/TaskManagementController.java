package railse.workForceMgmt.controller.employee;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import railse.workForceMgmt.dto.TaskDto;
import railse.workForceMgmt.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/tasks/management")
public class TaskManagementController
{
    private static final Logger log = LogManager.getLogger(TaskManagementController.class);

    @Autowired
    private TaskService taskService;

    @PostMapping("/comment/{taskId}")
    public void addComment(@PathVariable UUID taskId, @RequestBody String comment)
    {
        log.info("addComment method is called");
        taskService.addComment(taskId, comment);
    }

    @PostMapping("/status/{taskId}")
    public void changeStatus(@PathVariable UUID taskId, @RequestParam String status)
    {
        log.info("changeStatus method is called");
        taskService.changeStatus(taskId, status);
    }

    @GetMapping("/{taskId}")
    public TaskDto getDetails(@PathVariable UUID taskId)
    {
        log.info("getDetails method is called");
        return taskService.getTaskDetails(taskId);
    }

}
