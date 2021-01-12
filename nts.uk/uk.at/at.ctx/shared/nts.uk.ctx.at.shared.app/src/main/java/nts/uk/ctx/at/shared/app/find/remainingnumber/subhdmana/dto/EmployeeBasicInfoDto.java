package nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class EmployeeBasicInfoDto {
	private String employeeId;
	
	private String employeeCode;
	
	private String employeeName;
}
