package nts.uk.ctx.basic.app.command.organization.department;

import lombok.Data;

@Data
public class RemoveDepartmentCommand {

	private String departmentCode;

	private String historyId;

}
