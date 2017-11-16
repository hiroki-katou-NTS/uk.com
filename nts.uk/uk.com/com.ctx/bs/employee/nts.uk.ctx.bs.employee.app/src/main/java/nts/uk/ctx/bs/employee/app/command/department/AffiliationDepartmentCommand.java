package nts.uk.ctx.bs.employee.app.command.department;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class AffiliationDepartmentCommand {

	/** The id. */
	private String id;

	/** The period. */
	@PeregItem("")
	private GeneralDate startDate;
	
	@PeregItem("")
	private GeneralDate endDate;

	/** The employee id. */
	@PeregItem("")
	private String employeeId;

	/** The department id. */
	@PeregItem("")
	private String departmentId;
}
