/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;

/**
 * The Interface WorkTimezoneCommonSetSetMemento.
 */
public interface WorkTimezoneCommonSetSetMemento {

	/**
	 * Sets the zero H stradd calculate set.
	 *
	 * @param calcSet the new zero H stradd calculate set
	 */
	void setZeroHStraddCalculateSet(boolean calcSet);

	/**
	 * Sets the interval set.
	 *
	 * @param itvset the new interval set
	 */
	void setIntervalSet(IntervalTimeSetting itvset);

	/**
	 * Sets the sub hol time set.
	 *
	 * @param shtSet the new sub hol time set
	 */
	 void setSubHolTimeSet(List<WorkTimezoneOtherSubHolTimeSet> shtSet);

	/**
	 * Sets the medical set.
	 *
	 * @param list the new medical set
	 */
	void setMedicalSet(List<WorkTimezoneMedicalSet> list);

	/**
	 * Sets the go out set.
	 *
	 * @param set the new go out set
	 */
	void setGoOutSet(WorkTimezoneGoOutSet set);

	/**
	 * Sets the stamp set.
	 *
	 * @param set the new stamp set
	 */
	void setStampSet(WorkTimezoneStampSet set);

	/**
	 * Sets the late night time set.
	 *
	 * @param set the new late night time set
	 */
	void setLateNightTimeSet(WorkTimezoneLateNightTimeSet set);

	/**
	 * Sets the short time work set.
	 *
	 * @param set the new short time work set
	 */
	void setShortTimeWorkSet(WorkTimezoneShortTimeWorkSet set);

	/**
	 * Sets the extraord time set.
	 *
	 * @param set the new extraord time set
	 */
	void setExtraordTimeSet(WorkTimezoneExtraordTimeSet set);

	/**
	 * Sets the late early set.
	 *
	 * @param set the new late early set
	 */
	void setLateEarlySet(WorkTimezoneLateEarlySet set);

	/**
	 * Sets the holiday calculation.
	 *
	 * @param holidayCalculation the new holiday calculation
	 */
	void setHolidayCalculation(HolidayCalculation holidayCalculation);
	
	/**
	 * Sets the raising salary set.
	 *
	 * @param set the new raising salary set
	 */
	 void setRaisingSalarySet(Optional<BonusPaySettingCode> set);
}
