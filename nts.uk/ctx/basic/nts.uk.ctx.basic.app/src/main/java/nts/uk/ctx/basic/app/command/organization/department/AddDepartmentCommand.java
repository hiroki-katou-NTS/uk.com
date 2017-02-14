package nts.uk.ctx.basic.app.command.organization.department;

import java.util.Date;

import lombok.Data;

@Data
public class AddDepartmentCommand {

	private String departmentCode;

	private String historyId;

	private String externalCode;

	private String fullName;

	private String hierarchyCode;

	private String name;
	
	private Date startDate;
	
	private Date endDate;

}
