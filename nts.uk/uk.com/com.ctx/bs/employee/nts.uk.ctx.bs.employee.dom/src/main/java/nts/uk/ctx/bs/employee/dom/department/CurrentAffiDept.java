package nts.uk.ctx.bs.employee.dom.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 所属部門（兼務）
 * @author xuan vinh
 * */
@Getter
@AllArgsConstructor
public class CurrentAffiDept extends AggregateRoot{
	/**社員ID EmployeeId*/
	private String employeeId;
	/**所属部門ID AffiliationDepartmentID*/
	private String affiDeptId;
	/**部門ID Department id*/
	private String departmentId; 
	
	/** The period. */
	private DatePeriod period;
	
}
