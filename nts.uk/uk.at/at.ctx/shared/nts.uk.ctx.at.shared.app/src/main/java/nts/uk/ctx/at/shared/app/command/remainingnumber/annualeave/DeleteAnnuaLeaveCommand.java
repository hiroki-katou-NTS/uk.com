package nts.uk.ctx.at.shared.app.command.remainingnumber.annualeave;

import lombok.Getter;
import nts.uk.shr.pereg.app.PeregEmployeeId;

@Getter
public class DeleteAnnuaLeaveCommand {
	
	@PeregEmployeeId
	private String employeeId;

}
