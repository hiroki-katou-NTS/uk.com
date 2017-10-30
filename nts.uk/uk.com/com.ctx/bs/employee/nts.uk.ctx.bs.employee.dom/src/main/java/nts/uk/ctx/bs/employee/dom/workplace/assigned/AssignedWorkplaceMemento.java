package nts.uk.ctx.bs.employee.dom.workplace.assigned;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	public String getAssignedWorkplaceId();
	
	/**
	 * Get history id
	 * @return history id
	 * */
	public String getHistoryId();
	
	/**
	 * Get period
	 * @return period
	 * */
	public DatePeriod getPeriod();
	
	/**
	 * Set employee id
	 * @param employeeid
	 * */
	public void setEmployeeId(String employeeId);
	
	/**
	 * Set assigned workplace
	 * @param assigned workplace id
	 * */
	public void setAssignedWorkplaceId(String assignedWorkplaceId);
	
	/**
	 * Set history id
	 * @param history id
	 * */
	public void setHistoryId(String historyId);
	
	/**
	 * Set period
	 * @param period
	 * */
	public void setPeriod(DatePeriod period);
}
