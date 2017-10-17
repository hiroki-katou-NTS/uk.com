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
		if (obj.length == 2) {
			return new EmployeeToDeleteDto(obj[0].toString(), obj[1].toString());
		} else {
			return new EmployeeToDeleteDto(obj[0].toString(), obj[1].toString(), obj[2].toString());
		}
	}

	public EmployeeToDeleteDto(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}
}
