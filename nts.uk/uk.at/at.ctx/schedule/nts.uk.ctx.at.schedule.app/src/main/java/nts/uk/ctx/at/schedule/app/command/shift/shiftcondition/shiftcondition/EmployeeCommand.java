package nts.uk.ctx.at.schedule.app.command.shift.shiftcondition.shiftcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeCommand {
	private String empId;
	private String empCd;
	private String empName;
	private String workplaceId;
	private String wokplaceCd;
	private String workplaceName;
}
