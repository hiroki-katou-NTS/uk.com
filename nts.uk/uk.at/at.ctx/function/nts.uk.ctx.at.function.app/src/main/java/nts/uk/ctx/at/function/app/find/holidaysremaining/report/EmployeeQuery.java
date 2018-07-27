package nts.uk.ctx.at.function.app.find.holidaysremaining.report;

import lombok.Value;

@Value
public class EmployeeQuery {
	private String employeeCode;
	private String employeeId;
	private String employeeName;
	private String workplaceCode;
	private String workplaceId;
	private String workplaceName;
}
