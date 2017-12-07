package nts.uk.ctx.bs.employee.app.find.employee;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeToDeleteDto {

	// employeeCode
	private String code;

	// ReasonRemove
	private String reason;

	// ReasonRemove
	private String name;

	public static EmployeeToDeleteDto fromDomain(String scd, String reason) {
		return new EmployeeToDeleteDto(scd, reason);
	}

	public static EmployeeToDeleteDto fromDomain(String name) {
		return new EmployeeToDeleteDto(name);
	}
	

	public EmployeeToDeleteDto(String name) {
		super();
		this.name = name;
	}

	public EmployeeToDeleteDto(String code, String reason) {
		super();
		this.code = code;
		this.reason = reason;
	}
}
