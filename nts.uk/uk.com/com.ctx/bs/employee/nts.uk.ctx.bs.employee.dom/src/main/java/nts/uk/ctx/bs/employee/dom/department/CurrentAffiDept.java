package nts.uk.ctx.bs.employee.dom.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
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
	private DateHistoryItem dateHistoryItem;
	
	public static CurrentAffiDept createFromJavaFile(String employeeId, String affiDeptId, String departmentId, 
			String historyId, GeneralDate strD, GeneralDate endD){
		return new CurrentAffiDept(employeeId, affiDeptId, departmentId, 
				new DateHistoryItem(historyId, new DatePeriod(strD, endD)));
	}
	
}
