package nts.uk.ctx.bs.employee.app.find.employee;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeToDeleteDetailDto {
	
	private String datedelete;
	
	private String reason;
	
	private String newCode;

	private String newName;
	
	

	public static EmployeeToDeleteDetailDto fromDomain(Object[] obj) {
		return new EmployeeToDeleteDetailDto(obj[0].toString(), obj[1].toString(), obj[2].toString(), obj[3].toString());
	}
}
