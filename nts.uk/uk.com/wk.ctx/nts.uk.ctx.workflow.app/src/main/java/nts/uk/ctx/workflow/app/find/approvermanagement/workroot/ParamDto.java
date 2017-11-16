package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import lombok.Getter;

@Getter
public class ParamDto {
	private int rootType;
	private String workplaceId;
	private String employeeId;
	private int selectedMode;
}
