package nts.uk.ctx.bs.employee.dom.workplace.assigned;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	
	/** The history id. */
	//履歴ID
	private String historyId;

	/** The period. */
	//期間
	private DatePeriod period;
	
	public AssignedWorkplace createFromJavaType(String employeeId, String assignedWorkplaceId, String historyId, GeneralDate strD, GeneralDate endD){
		return new AssignedWorkplace(employeeId, assignedWorkplaceId, historyId, new DatePeriod(strD, endD));
	}
}
