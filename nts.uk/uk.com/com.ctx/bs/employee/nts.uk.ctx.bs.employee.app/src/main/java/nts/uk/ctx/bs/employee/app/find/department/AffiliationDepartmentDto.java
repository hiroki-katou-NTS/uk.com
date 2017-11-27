package nts.uk.ctx.bs.employee.app.find.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.PeregItem;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AffiliationDepartmentDto {
	
	/** The id. */
	@PeregItem("")
	private String id;

	/** The period. */
	@PeregItem("")
	private DatePeriod period;

	/** The employee id. */
	@PeregItem("")
	private String employeeId;

	/** The department id. */
	@PeregItem("")
	private String departmentId;
}
