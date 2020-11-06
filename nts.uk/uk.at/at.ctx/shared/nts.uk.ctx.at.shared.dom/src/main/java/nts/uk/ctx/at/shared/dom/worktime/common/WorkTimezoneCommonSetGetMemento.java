/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;

/**
 * The Interface WorkTimezoneCommonSetGetMemento.
 */
public interface WorkTimezoneCommonSetGetMemento {

	/**
	 * Gets the zero H stradd calculate set.
	 *
	 * @return the zero H stradd calculate set
	 */
	 boolean getZeroHStraddCalculateSet();

	/**
	 * Gets the interval set.
	 *
	 * @return the interval set
	 */
	 IntervalTimeSetting getIntervalSet();

	/**
	 * Gets the sub hol time set.
	 *
	 * @return the sub hol time set
	 */
	 List<WorkTimezoneOtherSubHolTimeSet> getSubHolTimeSet();

	/**
	 * Gets the medical set.
	 *
	 * @return the medical set
	 */
	 List<WorkTimezoneMedicalSet> getMedicalSet();

	/**
	 * Gets the go out set.
	 *
	 * @return the go out set
	 */
	 WorkTimezoneGoOutSet getGoOutSet();

	/**
	 * Gets the stamp set.
	 *
	 * @return the stamp set
	 */
	 WorkTimezoneStampSet getStampSet();

	/**
	 * Gets the late night time set.
	 *
	 * @return the late night time set
	 */
	 WorkTimezoneLateNightTimeSet getLateNightTimeSet();

	/**
	 * Gets the short time work set.
	 *
	 * @return the short time work set
	 */
	 WorkTimezoneShortTimeWorkSet getShortTimeWorkSet();

	/**
	 * Gets the extraord time set.
	 *
	 * @return the extraord time set
	 */
	 WorkTimezoneExtraordTimeSet getExtraordTimeSet();

	/**
	 * Gets the late early set.
	 *
	 * @return the late early set
	 */
	 WorkTimezoneLateEarlySet getLateEarlySet();

	/**
 	 * Gets the holiday calculation.
 	 *
 	 * @return the holiday calculation
 	 */
 	HolidayCalculation getHolidayCalculation();
 	
	/**
	 * Gets the raising salary set.
	 *
	 * @return the raising salary set
	 */
	 Optional<BonusPaySettingCode> getRaisingSalarySet();
}
