package nts.uk.ctx.pereg.app.find.deleteemployee;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeToDeleteDetailDto {
	
	private String code;

	private String name;
	
	private String reason;
	
	private String dateDelete;
	
	

	public static EmployeeToDeleteDetailDto fromDomain(String code,  String name , String reason , String dateDelete) {
		return new EmployeeToDeleteDetailDto(code, name, reason, dateDelete);
	}
}
