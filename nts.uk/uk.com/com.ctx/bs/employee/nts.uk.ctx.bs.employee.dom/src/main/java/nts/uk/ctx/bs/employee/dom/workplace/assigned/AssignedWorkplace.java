package nts.uk.ctx.bs.employee.dom.workplace.assigned;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Getter
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
	
	public AssignedWorkplace(AssignedWorkplaceMemento memento){
		this.employeeId = memento.getEmployeeId();
		this.assignedWorkplaceId = memento.getAssignedWorkplaceId();
		this.historyId = memento.getHistoryId();
		this.period = memento.getPeriod();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(AssignedWorkplaceMemento memento){
		memento.setEmployeeId(this.employeeId);
		memento.setAssignedWorkplaceId(this.assignedWorkplaceId);
		memento.setHistoryId(this.historyId);
		memento.setPeriod(this.period);
	}
}
