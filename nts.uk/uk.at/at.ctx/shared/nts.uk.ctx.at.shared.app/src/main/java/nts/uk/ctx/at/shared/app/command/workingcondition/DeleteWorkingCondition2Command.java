package nts.uk.ctx.at.shared.app.command.workingcondition;

import lombok.Getter;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class DeleteWorkingCondition2Command{
	
	@PeregRecordId
	private String histId;
	
	@PeregEmployeeId
	private String employeeId;
	
}
