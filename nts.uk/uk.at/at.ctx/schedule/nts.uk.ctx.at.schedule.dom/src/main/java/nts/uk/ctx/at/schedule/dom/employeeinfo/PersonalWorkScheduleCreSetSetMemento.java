/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.employeeinfo;

/**
 * The Interface PersonalWorkScheduleCreSetSetMemento.
 */
public interface PersonalWorkScheduleCreSetSetMemento {

	/**
	 * Sets the basic create method.
	 *
	 * @param basicCreateMethod the new basic create method
	 */
	public void setBasicCreateMethod(WorkScheduleBasicCreMethod basicCreateMethod);
	
	
	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	public void setEmployeeId(String employeeId);
	
	
	/**
	 * Sets the monthly pattern work schedule cre.
	 *
	 * @param monthlyPatternWorkScheduleCre the new monthly pattern work schedule cre
	 */
	public void setMonthlyPatternWorkScheduleCre(MonthlyPatternWorkScheduleCre monthlyPatternWorkScheduleCre);
	
	
	/**
	 * Sets the work schedule bus cal.
	 *
	 * @param workScheduleBusCal the new work schedule bus cal
	 */
	public void setWorkScheduleBusCal(WorkScheduleBusCal workScheduleBusCal);
}
