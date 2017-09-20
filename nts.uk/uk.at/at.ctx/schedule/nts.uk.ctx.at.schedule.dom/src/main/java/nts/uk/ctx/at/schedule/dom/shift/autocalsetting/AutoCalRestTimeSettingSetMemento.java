/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.autocalsetting;

/**
 * The Interface AutoCalRestTimeSettingSetMemento.
 */
public interface AutoCalRestTimeSettingSetMemento {
	
	/**
	 * Sets the rest time.
	 *
	 * @param restTime the new rest time
	 */
	void  setRestTime(AutoCalSetting restTime);
	
	/**
	 * Sets the late night time.
	 *
	 * @param restTime the new late night time
	 */
	void  setLateNightTime(AutoCalSetting lateNightTime);
	
	
}
