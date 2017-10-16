package nts.uk.ctx.bs.employee.dom.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

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
	/**start date*/
	private GeneralDate startDate;
	/**end Date*/
	private GeneralDate endDate;
	
}
