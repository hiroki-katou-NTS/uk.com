package nts.uk.ctx.bs.employee.app.command.workplace.affiliate;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class AffWorkplaceHistoryCommand {

	
	/** 期間 */
	@PeregItem("")
	private GeneralDate startDate;
	
	@PeregItem("")
	private GeneralDate endDate;

	/** 社員ID */
	@PeregEmployeeId
	private String employeeId;

	/** 職場ID */
	@PeregRecordId
	private String workplaceId;
}
