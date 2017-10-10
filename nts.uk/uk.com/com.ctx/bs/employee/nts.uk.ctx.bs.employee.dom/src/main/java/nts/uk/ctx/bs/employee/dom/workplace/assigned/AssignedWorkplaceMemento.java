package nts.uk.ctx.bs.employee.dom.workplace.assigned;

import nts.uk.ctx.bs.employee.dom.workplace.HistoryId;
import nts.uk.ctx.bs.employee.dom.workplace.Period;

public interface AssignedWorkplaceMemento {

	/**
	 * Get employee id
	 * @return employee id
	 * */
	public String getEmployeeId();
	
	/**
	 * Get assigned workplace id
	 * @return assigned workplace id
	 * */
	public AssignedWorkplaceId getAssignedWorkplaceId();
	
	/**
	 * Get history id
	 * @return history id
	 * */
	public HistoryId getHistoryId();
	
	/**
	 * Get period
	 * @return period
	 * */
	public Period getPeriod();
	
	/**
	 * Set employee id
	 * @param employeeid
	 * */
	public void setEmployeeId(String employeeId);
	
	/**
	 * Set assigned workplace
	 * @param assigned workplace id
	 * */
	public void setAssignedWorkplaceId(AssignedWorkplaceId assignedWorkplaceId);
	
	/**
	 * Set history id
	 * @param history id
	 * */
	public void setHistoryId(HistoryId historyId);
	
	/**
	 * Set period
	 * @param period
	 * */
	public void setPeriod(Period period);
}
