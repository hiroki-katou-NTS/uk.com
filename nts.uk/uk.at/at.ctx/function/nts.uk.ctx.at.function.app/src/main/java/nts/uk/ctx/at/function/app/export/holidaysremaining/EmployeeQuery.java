package nts.uk.ctx.at.function.app.export.holidaysremaining;

import lombok.Getter;
import lombok.Value;

@Value
@Getter
public class EmployeeQuery {
	private String employeeCode;
	private String employeeId;
	private String employeeName;
	private String workplaceCode;
	private String workplaceId;
	private String workplaceName;
}
