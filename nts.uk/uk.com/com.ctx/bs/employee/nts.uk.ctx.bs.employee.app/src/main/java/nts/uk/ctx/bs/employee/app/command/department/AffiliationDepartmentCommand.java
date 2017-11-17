package nts.uk.ctx.bs.employee.app.command.department;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class AffiliationDepartmentCommand {

	/** The id. */
	@PeregRecordId
	private String id;

	/** The period. */
	@PeregItem("")
	private GeneralDate startDate;
	
	@PeregItem("")
	private GeneralDate endDate;

	/** The employee id. */
	@PeregEmployeeId
	private String employeeId;

	/** The department id. */
	@PeregItem("")
	private String departmentId;
}
