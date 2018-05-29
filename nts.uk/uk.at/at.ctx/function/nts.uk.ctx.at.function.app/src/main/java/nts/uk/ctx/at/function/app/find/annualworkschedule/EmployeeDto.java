package nts.uk.ctx.at.function.app.find.annualworkschedule;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class EmployeeDto {
	private String employeeId;
	private String code;
	private String name;
	private String workplaceName;
}
