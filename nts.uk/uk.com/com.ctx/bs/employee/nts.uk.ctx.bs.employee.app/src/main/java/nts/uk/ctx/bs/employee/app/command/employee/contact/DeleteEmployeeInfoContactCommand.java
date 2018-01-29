package nts.uk.ctx.bs.employee.app.command.employee.contact;

import lombok.Getter;
import nts.uk.shr.pereg.app.PeregEmployeeId;

@Getter
public class DeleteEmployeeInfoContactCommand {
	//社員ID
	@PeregEmployeeId
	private String sid;
}
