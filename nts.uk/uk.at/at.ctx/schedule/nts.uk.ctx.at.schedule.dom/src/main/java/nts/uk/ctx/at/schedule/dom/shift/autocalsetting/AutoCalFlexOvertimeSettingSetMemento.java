/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.autocalsetting;

/**
 * The Interface AutoCalFlexOvertimeSettingSetMemento.
 */
public interface AutoCalFlexOvertimeSettingSetMemento {
	/**
	 * Sets the flex ot time.
	 *
	 * @param flexOtTime the new flex ot time
	 */
	void  setFlexOtTime(AutoCalSetting flexOtTime);
	
	/**
	 * Sets the flex ot night time.
	 *
	 * @param flexOtNightTime the new flex ot night time
	 */
	void  setFlexOtNightTime(AutoCalSetting flexOtNightTime);
	
}
