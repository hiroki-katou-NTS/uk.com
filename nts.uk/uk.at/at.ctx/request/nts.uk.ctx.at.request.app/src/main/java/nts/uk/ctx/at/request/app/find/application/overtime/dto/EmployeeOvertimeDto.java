package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeOvertimeDto {
	/**
	 * employeeIDs
	 */
	private String employeeIDs;
	
	/**
	 * employeeName
	 */
	private String employeeName;

}
