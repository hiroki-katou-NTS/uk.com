/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

/**
 * The Interface BasicWorkSettingSetMemento.
 */
public interface BasicWorkSettingSetMemento {
	
	/**
	 * Sets the work type code.
	 *
	 * @param worktypeCode the new work type code
	 */
	void setWorkTypeCode(WorktypeCode worktypeCode);
	
	/**
	 * Sets the sift code.
	 *
	 * @param workingCode the new sift code
	 */
	void setSiftCode(WorkingCode workingCode);
	
	/**
	 * Sets the work day division.
	 *
	 * @param workdayDivision the new work day division
	 */
	void setWorkDayDivision(WorkdayDivision workdayDivision);
}
