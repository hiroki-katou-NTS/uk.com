package nts.uk.ctx.bs.employee.app.find.employee;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeToDeleteDto {
	
	private String code;

	private String name;
	
	private String id;

	public static EmployeeToDeleteDto fromDomain(Object[] obj) {
		return new EmployeeToDeleteDto(obj[0].toString(), obj[1].toString(), obj[2].toString());
	}
}
