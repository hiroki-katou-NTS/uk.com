/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.employeeinfo;

/**
 * The Interface PersonalWorkScheduleCreSetGetMemento.
 */
public interface PersonalWorkScheduleCreSetGetMemento {

	/**
	 * Gets the basic create method.
	 *
	 * @return the basic create method
	 */
	public WorkScheduleBasicCreMethod getBasicCreateMethod();
	
	
	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	public String getEmployeeId();
	
	
	/**
	 * Gets the monthly pattern work schedule cre.
	 *
	 * @return the monthly pattern work schedule cre
	 */
	public MonthlyPatternWorkScheduleCre getMonthlyPatternWorkScheduleCre();
	
	
	/**
	 * Gets the work schedule bus cal.
	 *
	 * @return the work schedule bus cal
	 */
	public WorkScheduleBusCal getWorkScheduleBusCal();
}
