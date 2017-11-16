package nts.uk.ctx.bs.employee.app.command.workplace.affiliate;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class AffWorkplaceHistoryCommand {

	
	/** 期間 */
	@PeregItem("")
	private GeneralDate startDate;
	
	@PeregItem("")
	private GeneralDate endDate;

	/** 社員ID */
	@PeregItem("")
	private String employeeId;

	/** 職場ID */
	@PeregItem("")
	private String workplaceId;
}
