package nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.taskassign.taskassignemployee;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TaskAssignEmployeeCommand {
    private int taskFrameNo;
    private String taskCode;
    private List<String> employeeIds;
}
