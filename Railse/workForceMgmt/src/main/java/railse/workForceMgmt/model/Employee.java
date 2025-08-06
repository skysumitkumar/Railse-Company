package railse.workForceMgmt.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee
{
    private String employeeId;
    private String employeeName;
    private String role;
}