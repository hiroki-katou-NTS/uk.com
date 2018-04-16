package nts.uk.ctx.at.record.app.command.remainingnumber.otherhdinfo;

import lombok.Getter;
import nts.uk.shr.pereg.app.PeregEmployeeId;

@Getter
public class DeleteOtherHolidayInfoCommand {

	@PeregEmployeeId
	private String employeeId;
}
