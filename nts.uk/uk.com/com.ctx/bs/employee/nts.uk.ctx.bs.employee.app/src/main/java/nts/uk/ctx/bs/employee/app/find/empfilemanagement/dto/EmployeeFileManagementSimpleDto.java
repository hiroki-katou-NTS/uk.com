package nts.uk.ctx.bs.employee.app.find.empfilemanagement.dto;

import lombok.Data;

@Data
public class EmployeeFileManagementSimpleDto {
	/**employee id*/
	private String employeeId;
	/**file id*/
	private String fileId;
	/**file type*/
	private String fileType;
}
