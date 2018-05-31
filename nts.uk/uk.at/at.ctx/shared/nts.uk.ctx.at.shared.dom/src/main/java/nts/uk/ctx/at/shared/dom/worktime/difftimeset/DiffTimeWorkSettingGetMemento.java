/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;

/**
 * The Interface DiffTimeWorkSettingGetMemento.
 */
public interface DiffTimeWorkSettingGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();

	/**
	 * Gets the work time code.
	 *
	 * @return the work time code
	 */
	WorkTimeCode getWorkTimeCode();

	/**
	 * Gets the rest set.
	 *
	 * @return the rest set
	 */
	FixedWorkRestSet getRestSet();

	/**
	 * Gets the dayoff work timezone.
	 *
	 * @return the dayoff work timezone
	 */
	DiffTimeDayOffWorkTimezone getDayoffWorkTimezone();

	/**
	 * Gets the common set.
	 *
	 * @return the common set
	 */
	WorkTimezoneCommonSet getCommonSet();

	/**
	 * Checks if is checks if is use half day shift.
	 *
	 * @return true, if is checks if is use half day shift
	 */
	boolean isIsUseHalfDayShift();

	/**
	 * Gets the change extent.
	 *
	 * @return the change extent
	 */
	EmTimezoneChangeExtent getChangeExtent();

	/**
	 * Gets the half day work timezones.
	 *
	 * @return the half day work timezones
	 */
	List<DiffTimeHalfDayWorkTimezone> getHalfDayWorkTimezones();

	/**
	 * Gets the stamp reflect timezone.
	 *
	 * @return the stamp reflect timezone
	 */
	DiffTimeWorkStampReflectTimezone getStampReflectTimezone();

	/**
	 * Gets the overtime setting.
	 *
	 * @return the overtime setting
	 */
	LegalOTSetting getOvertimeSetting();

	/**
	 * Gets the calculation setting.
	 *
	 * @return the calculation setting
	 */
	Optional<FixedWorkCalcSetting> getCalculationSetting();
}
