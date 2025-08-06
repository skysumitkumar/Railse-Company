package railse.workForceMgmt.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskRequest
{
    private String managerId;
    private String employeeId;
    private String employeeName;
    private String taskId;
    private String title;
    private LocalDate startDate;
    private LocalDate dueDate;
}