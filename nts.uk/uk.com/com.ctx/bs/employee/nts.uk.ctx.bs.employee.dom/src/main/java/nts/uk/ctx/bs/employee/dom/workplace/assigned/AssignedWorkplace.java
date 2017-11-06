package nts.uk.ctx.bs.employee.dom.workplace.assigned;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * 所属職場履歴
 * 
 * @author xuan vinh
 *
 */

@Getter
@AllArgsConstructor
public class AssignedWorkplace extends AggregateRoot{
	
	/**Employee id*/
	//// 社員ID
	private String employeeId;
	
	/***
	 * assigned workplace id
	 */
	//職場ID
	private String assignedWorkplaceId;
	
	private List<DateHistoryItem> dateHistoryItem;
	
	
}
