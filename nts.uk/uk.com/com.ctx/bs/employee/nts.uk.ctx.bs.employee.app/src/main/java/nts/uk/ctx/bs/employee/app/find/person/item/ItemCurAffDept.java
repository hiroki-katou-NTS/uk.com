package nts.uk.ctx.bs.employee.app.find.person.item;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;
import nts.uk.shr.com.history.DateHistoryItem;


@Getter
public class ItemCurAffDept extends CtgItemFixDto{
	/**社員ID EmployeeId*/
	private String employeeId;
	/**所属部門ID AffiliationDepartmentID*/
	private String affiDeptId;
	/**部門ID Department id*/
	private String departmentId; 
	/** The DateHistoryItem. */
	private List<DateHistoryItem> dateHistoryItem;
	
	private ItemCurAffDept(String employeeId, String affiDeptId, String departmentId, List<DateHistoryItem> dateHistoryItem){
		super();
		this.ctgItemType = CtgItemType.CURRENT_AFF_DEPARTMENT;
		this.employeeId = employeeId;
		this.affiDeptId = affiDeptId;
		this.departmentId = departmentId;
		this.dateHistoryItem = dateHistoryItem;
	}
	
	public static ItemCurAffDept createFromJavaType(String employeeId, String affiDeptId, String departmentId, List<DateHistoryItem> dateHistoryItem){
		return new ItemCurAffDept(employeeId, affiDeptId, departmentId, dateHistoryItem);
	}
}
