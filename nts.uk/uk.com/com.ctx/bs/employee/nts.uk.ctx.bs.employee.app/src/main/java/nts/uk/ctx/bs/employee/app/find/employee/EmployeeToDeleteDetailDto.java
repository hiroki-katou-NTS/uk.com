package nts.uk.ctx.bs.employee.app.find.employee;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeToDeleteDetailDto {
	
	private String newCode;

	private String newName;
	
	private String reason;
	
	private String datedelete;
	
	

	public static EmployeeToDeleteDetailDto fromDomain(String newCode,  String newName , String reason , String datedelete) {
		return new EmployeeToDeleteDetailDto(newCode, newName, reason, datedelete);
	}
}
