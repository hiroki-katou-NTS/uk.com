package nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype;

import lombok.Getter;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class DeleteBusinessWorkTypeOfHistoryCommand {
	@PeregRecordId
	private String historyId;
	@PeregEmployeeId
	private String employeeId;
}
