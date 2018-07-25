package nts.uk.ctx.at.function.app.export.annualworkschedule;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class EmployeeDto {
	private String employeeCode;
	private String name;
	private String workplaceName;
}
