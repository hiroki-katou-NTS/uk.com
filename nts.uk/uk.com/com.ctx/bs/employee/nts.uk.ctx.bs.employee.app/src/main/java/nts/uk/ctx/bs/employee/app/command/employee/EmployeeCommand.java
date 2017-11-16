package nts.uk.ctx.bs.employee.app.command.employee;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
@Getter
	
public class EmployeeCommand {
	/** The List JobEntryHistory 入社履歴 */
	
	private String companyId;

	/** The employeeId */
	@PeregEmployeeId
	private String sId;

	/** The HiringType */
	@PeregItem("")
	private String hiringType;

	/** The RetireDate */
	@PeregItem("")
	private GeneralDate retirementDate;

	/** The EntryDate */
	@PeregItem("")
	private GeneralDate joinDate;

	/** The AdoptDate */
	@PeregItem("")
	private GeneralDate adoptDate;
}
