package nts.uk.ctx.bs.employee.dom.department;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousResidentHistory;

/**
 * 所属部門（兼務）
 * @author xuan vinh
 * */
@Getter
@AllArgsConstructor
public class CurrentAffiDept extends AggregateRoot implements ContinuousResidentHistory{
	/**社員ID EmployeeId*/
	private String employeeId;
	/**所属部門ID AffiliationDepartmentID*/
	private String affiDeptId;
	/**部門ID Department id*/
	private String departmentId;
	/** The DateHistoryItem. */
	private List<DateHistoryItem> dateHistoryItem;
	
	@Override
	public List items() {
		return this.dateHistoryItem;
	}
	
}
