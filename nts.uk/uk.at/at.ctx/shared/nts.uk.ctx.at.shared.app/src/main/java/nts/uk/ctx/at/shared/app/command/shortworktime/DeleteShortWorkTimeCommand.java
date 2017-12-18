/**
 * 
 */
package nts.uk.ctx.at.shared.app.command.shortworktime;

import lombok.Getter;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class DeleteShortWorkTimeCommand{
	
	@PeregRecordId
	private String historyId;
	
	@PeregEmployeeId
	private String employeeId;
}
