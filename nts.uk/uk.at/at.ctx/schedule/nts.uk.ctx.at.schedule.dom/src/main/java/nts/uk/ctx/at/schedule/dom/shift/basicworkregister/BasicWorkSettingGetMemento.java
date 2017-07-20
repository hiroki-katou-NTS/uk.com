/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

/**
 * The Interface BasicWorkSettingGetMemento.
 */
public interface BasicWorkSettingGetMemento {
	
	/**
	 * Gets the work typecode.
	 *
	 * @return the work typecode
	 */
	WorktypeCode getWorkTypecode();
	
	/**
	 * Gets the sift code.
	 *
	 * @return the sift code
	 */
	WorkingCode getSiftCode();
	
	/**
	 * Gets the work day division.
	 *
	 * @return the work day division
	 */
	WorkdayDivision getWorkDayDivision();
}
