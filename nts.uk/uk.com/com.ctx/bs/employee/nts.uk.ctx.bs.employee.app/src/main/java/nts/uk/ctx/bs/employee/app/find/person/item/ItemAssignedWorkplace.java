package nts.uk.ctx.bs.employee.app.find.person.item;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;
import nts.uk.shr.com.history.DateHistoryItem;

@Getter
public class ItemAssignedWorkplace extends CtgItemFixDto{
	/**Employee id*/
	//// 社員ID
	private String employeeId;
	
	/***
	 * assigned workplace id
	 */
	//職場ID
	private String assignedWorkplaceId;
	
	private List<DateHistoryItem> dateHistoryItem;
	
	private ItemAssignedWorkplace(String employeeId, String assignedWorkplaceId, List<DateHistoryItem> dateHistoryItem){
		super();
		this.ctgItemType = CtgItemType.ASSIGNED_WORKPLACE;
		this.employeeId = employeeId;
		this.assignedWorkplaceId = assignedWorkplaceId;
		this.dateHistoryItem = dateHistoryItem;
	}
	
	public static ItemAssignedWorkplace createFromJavaType(String employeeId, String assignedWorkplaceId, List<DateHistoryItem> dateHistoryItem){
		return new ItemAssignedWorkplace(employeeId, assignedWorkplaceId, dateHistoryItem);
	}
}
