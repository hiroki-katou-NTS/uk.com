package nts.uk.ctx.at.shared.app.find.scherec.taskmanagement.taskassign.taskassignemployee;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskAssignEmployeeDto {
    private String employeeId;
    private int taskFrameNo;
    private String taskCode;
}
