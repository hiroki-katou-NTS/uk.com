package nts.uk.ctx.hr.develop.app.employee;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@NoArgsConstructor
public class EmployeeInformationQuery {

	private String employeeId;

	private String personId;

	private String baseDate;
	private Boolean getDepartment;
	private Boolean getPosition;
	private Boolean getEmployment;

	public GeneralDate getBaseDate() {
		return GeneralDate.fromString(baseDate, "yyyy/MM/dd");

	}
}
