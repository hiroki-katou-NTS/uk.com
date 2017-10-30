package nts.uk.ctx.bs.employee.app.find.person.item;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;

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
	
	/** The history id. */
	//履歴ID
	private String historyId;
	
	/**Start date*/
	private GeneralDate strD;
	
	/**End date*/
	private GeneralDate endD;
	
	private ItemAssignedWorkplace(String employeeId, String assignedWorkplaceId, String historyId, GeneralDate strD, GeneralDate endD){
		super();
		this.ctgItemType = CtgItemType.ASSIGNED_WORKPLACE;
		this.employeeId = employeeId;
		this.assignedWorkplaceId = assignedWorkplaceId;
		this.historyId = historyId;
		this.strD = strD;
		this.endD = endD;
	}
	
	public static ItemAssignedWorkplace createFromJavaType(String employeeId, String assignedWorkplaceId, String historyId, GeneralDate strD, GeneralDate endD){
		return new ItemAssignedWorkplace(employeeId, assignedWorkplaceId, historyId, strD, endD);
	}
}
