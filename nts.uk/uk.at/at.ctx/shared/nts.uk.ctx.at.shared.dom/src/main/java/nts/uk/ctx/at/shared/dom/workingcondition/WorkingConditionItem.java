package nts.uk.ctx.at.shared.dom.workingcondition;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class WorkingConditionItem extends AggregateRoot {
	
	/** The schedule management atr. */
	private NotUseAtr scheduleManagementAtr;
	
	/** The vacation added time atr. */
	private NotUseAtr vacationAddedTimeAtr;
	
	/** The labor system. */
	private WorkingSystem laborSystem;
	
	/** The work category. */
	private PersonalWorkCategory workCategory;
	
	/** The contract time. */
	private LaborContractTime contractTime;
	
	/** The auto interval set atr. */
	private NotUseAtr autoIntervalSetAtr;
	
	/** The history id. */
	private String historyId;
	
	/** The work day of week. */
	private PersonalDayOfWeek workDayOfWeek;
	
	/** The employee id. */
	private String employeeId;
	
	/** The auto stamp set atr. */
	private NotUseAtr autoStampSetAtr;
	
	/** The schedule method. */
	private ScheduleMethod scheduleMethod;
	
	/** The holiday add time set. */
	private BreakdownTimeDay holidayAddTimeSet;
	
	/**
	 * Instantiates a new working condition.
	 *
	 * @param memento the memento
	 */
	public WorkingConditionItem(WorkingConditionItemGetMemento memento){
		
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WorkingConditionItemSetMemento memento) {
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((historyId == null) ? 0 : historyId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkingConditionItem other = (WorkingConditionItem) obj;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		
		if (historyId == null) {
			if (other.historyId != null)
				return false;
		} else if (!historyId.equals(other.historyId))
			return false;
		
		return true;
	}
}
