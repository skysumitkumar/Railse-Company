package railse.workForceMgmt.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import railse.workForceMgmt.dto.CreateTaskRequest;
import railse.workForceMgmt.dto.TaskDto;
import railse.workForceMgmt.model.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TaskService
{
    private static final Logger log = LogManager.getLogger(TaskService.class);

    private final Map<UUID, Task> taskRepo = new HashMap<>();

    public Task createTask(CreateTaskRequest request)
    {
        log.info("createTask service is called");
        Task task = new Task(request.getManagerId(),
                            request.getEmployeeId(),
                            request.getEmployeeName(),
                            UUID.randomUUID(),
                            request.getTitle(),
                            request.getStartDate(),
                            request.getDueDate(),
                            "ACTIVE",
                            "MEDIUM",
                            new ArrayList<>(),
                            new ArrayList<>()
                            );
        task.getHistory().add("TaskId: "+task.getTaskId()+" Task title: "+request.getTitle()+" Created by ManagerId "+request.getManagerId()+" Assign to EmployeeId: "+request.getEmployeeId()+" EmployeeName: " + request.getEmployeeName());
        taskRepo.put(task.getTaskId(), task);
        return task;
    }

    public List<TaskDto> getTasksByDateRange(LocalDate from, LocalDate to)
    {
        log.info("getTasksByDateRange service is called");
        List<TaskDto> result = new ArrayList<>();

        for(Task task : taskRepo.values())
        {
            if(Objects.equals(task.getStatus(), "CANCELLED"))
            {
                continue;
            }

            LocalDate startDate = task.getStartDate();
            LocalDate dueDate = task.getDueDate();
            boolean isInRange = (startDate.isEqual(from) || startDate.isAfter(from)) &&
                                (dueDate.isEqual(to) || dueDate.isBefore(to));

            boolean isBeforeFromAndActive = startDate.isBefore(from) && Objects.equals(task.getStatus(), "ACTIVE");

            if(isInRange || isBeforeFromAndActive)
            {
                result.add(toDto(task));
            }
        }
        return result;
    }

    public Task reassignTask(UUID taskId, String employeeName,String employeeId,LocalDate startDate,LocalDate dueDate)
    {
        log.info("reassignTask service is called");
        Task task = taskRepo.get(taskId);
        if(task != null && Objects.equals(task.getStatus(), "ACTIVE"))
        {
            task.setStatus("CANCELLED");

            Task newTask = new Task(task.getManagerId(),
                                    employeeId,
                                    employeeName,
                                    UUID.randomUUID(),
                                    task.getTitle(),
                                    startDate,
                                    dueDate,
                                "ACTIVE",
                                "HIGH",
                                    new ArrayList<>(),
                                    new ArrayList<>(List.of("Reassigned Task from EmployeeId: "+task.getEmployeeId()+" to EmployeeId: "+employeeId))
            );
            task.getHistory().add("Reassigned Task from EmployeeId: "+task.getEmployeeId()+" to EmployeeId "+employeeId);
            taskRepo.put(newTask.getTaskId(), newTask);
            return newTask;
        }
        return null;
    }

    public void updatePriority(UUID taskId, String priority)
    {
        log.info("updatePriority service is called");
        Task task = taskRepo.get(taskId);
        if(task != null&& Objects.equals(task.getStatus(), "ACTIVE"))
        {
            task.setPriority(priority);
            task.getHistory().add("Task ID: "+taskId+" EmployeeId: "+task.getEmployeeId()+" Priority updated to " + priority);
        }
    }

    public List<TaskDto> getTasksByPriority(String priority)
    {
        log.info("getTasksByPriority service is called");
        List<TaskDto> result = new ArrayList<>();

        for(Task task : taskRepo.values())
        {
            if(Objects.equals(task.getStatus(), "CANCELLED") || !Objects.equals(task.getPriority(), priority))
            {
                continue;
            }

            result.add(toDto(task));
        }
        return result;
    }

    public void addComment(UUID taskId, String comment)
    {
        log.info("addComment service is called");
        Task task = taskRepo.get(taskId);
        if(task != null)
        {
            task.getComment().add(comment);
            task.getHistory().add("Task ID: "+taskId+" EmployeeId: "+task.getEmployeeId()+" Comment added: " + comment);
        }
    }

    public TaskDto getTaskDetails(UUID taskId)
    {
        log.info("getTasksDetails service is called");
        Task task = taskRepo.get(taskId);
        return task != null ? toDto(task) : null;
    }

    private TaskDto toDto(Task task)
    {
        return new TaskDto(task.getManagerId(),
                            task.getEmployeeId(),
                            task.getEmployeeName(),
                            task.getTaskId(),
                            task.getTitle(),
                            task.getStartDate(),
                            task.getDueDate(),
                            task.getStatus(),
                            task.getPriority(),
                            task.getComment(),
                            task.getHistory()
                            );
    }

    public void changeStatus(UUID taskId, String status)
    {
        log.info("changeStatus service is called");
        Task task = taskRepo.get(taskId);
        if(task != null&& Objects.equals(task.getStatus(), "ACTIVE"))
        {
            task.setStatus(status);
            task.getHistory().add("Task ID: "+taskId+" EmployeeId: "+task.getEmployeeId()+" Status updated to " + status);
        }
    }

    public List<TaskDto> getAllTaskDetails()
    {
        log.info("getAllTaskDetails service is called");
        List<TaskDto> result = new ArrayList<>();

        for(Task task : taskRepo.values())
        {
            result.add(toDto(task));
        }
        return result;
    }
}
