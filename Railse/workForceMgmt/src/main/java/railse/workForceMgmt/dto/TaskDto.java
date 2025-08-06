package railse.workForceMgmt.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto
{
    private String managerId;
    private String employeeId;
    private String employeeName;
    private UUID taskId;
    private String title;
    private LocalDate startDate;
    private LocalDate dueDate;
    public String status;
    public String priority;
    private List<String> comment = new ArrayList<>();
    private List<String> history = new ArrayList<>();

}
