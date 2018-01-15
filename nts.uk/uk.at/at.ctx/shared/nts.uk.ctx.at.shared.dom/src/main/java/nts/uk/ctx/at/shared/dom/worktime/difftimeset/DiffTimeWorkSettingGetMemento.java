/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;

/**
 * The Interface DiffTimeWorkSettingGetMemento.
 */
public interface DiffTimeWorkSettingGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public String getCompanyId();

	/**
	 * Gets the work time code.
	 *
	 * @return the work time code
	 */
	public WorkTimeCode getWorkTimeCode();

	/**
	 * Gets the rest set.
	 *
	 * @return the rest set
	 */
	public FixedWorkRestSet getRestSet();

	/**
	 * Gets the dayoff work timezone.
	 *
	 * @return the dayoff work timezone
	 */
	public DiffTimeDayOffWorkTimezone getDayoffWorkTimezone();

	/**
	 * Gets the common set.
	 *
	 * @return the common set
	 */
	public WorkTimezoneCommonSet getCommonSet();

	/**
	 * Checks if is checks if is use half day shift.
	 *
	 * @return true, if is checks if is use half day shift
	 */
	public boolean isIsUseHalfDayShift();

	/**
	 * Gets the change extent.
	 *
	 * @return the change extent
	 */
	public EmTimezoneChangeExtent getChangeExtent();

	/**
	 * Gets the half day work timezones.
	 *
	 * @return the half day work timezones
	 */
	public List<DiffTimeHalfDayWorkTimezone> getHalfDayWorkTimezones();

	/**
	 * Gets the stamp reflect timezone.
	 *
	 * @return the stamp reflect timezone
	 */
	public DiffTimeWorkStampReflectTimezone getStampReflectTimezone();

	/**
	 * Gets the overtime setting.
	 *
	 * @return the overtime setting
	 */
	public LegalOTSetting getOvertimeSetting();
}
