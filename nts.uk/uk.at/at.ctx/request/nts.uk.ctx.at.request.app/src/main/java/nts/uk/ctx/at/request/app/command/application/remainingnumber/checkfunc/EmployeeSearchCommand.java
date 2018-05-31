package nts.uk.ctx.at.request.app.command.application.remainingnumber.checkfunc;

import lombok.Data;

@Data
public class EmployeeSearchCommand {
	private String employeeId;

	private String employeeCode;

	private String employeeName;

	private String workplaceCode;

	private String workplaceId;

	private String workplaceName;
}
