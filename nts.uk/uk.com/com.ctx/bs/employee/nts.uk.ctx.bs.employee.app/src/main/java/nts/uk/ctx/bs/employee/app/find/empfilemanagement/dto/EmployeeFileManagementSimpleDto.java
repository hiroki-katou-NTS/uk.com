package nts.uk.ctx.bs.employee.app.find.empfilemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeFileManagementSimpleDto {
	/**employee id*/
	private String employeeId;
	/**file id*/
	private String fileId;
	/**file type*/
	private int fileType;
}
