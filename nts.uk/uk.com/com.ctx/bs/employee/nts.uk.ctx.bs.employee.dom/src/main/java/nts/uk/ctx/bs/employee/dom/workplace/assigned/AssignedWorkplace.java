package nts.uk.ctx.bs.employee.dom.workplace.assigned;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.employee.dom.workplace.HistoryId;
import nts.uk.ctx.bs.employee.dom.workplace.Period;

@Getter
public class AssignedWorkplace extends AggregateRoot{
	/**Employee id*/
	//// 社員ID
	private String employeeId;
	
	/***
	 * assigned workplace id
	 */
	//職場ID
	private AssignedWorkplaceId assignedWorkplaceId;
	
	/** The history id. */
	//履歴ID
	private HistoryId historyId;

	/** The period. */
	//期間
	private Period period;
	
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
