/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.HalfDayWorkSet;

import java.util.List;

/**
 * The Interface FlexWorkSettingGetMemento.
 */
public interface FlexWorkSettingGetMemento {
	
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
	 * Gets the core time setting.
	 *
	 * @return the core time setting
	 */
	 CoreTimeSetting getCoreTimeSetting();

	/**
	 * Gets the rest setting.
	 *
	 * @return the rest setting
	 */
	 FlowWorkRestSetting getRestSetting();

	/**
	 * Gets the offday work time.
	 *
	 * @return the offday work time
	 */
	 FlexOffdayWorkTime getOffdayWorkTime();

	/**
	 * Gets the common setting.
	 *
	 * @return the common setting
	 */
	 WorkTimezoneCommonSet getCommonSetting();

	/**
	 * Gets the use half day shift.
	 *
	 * @return the use half day shift
	 */
	 HalfDayWorkSet getUseHalfDayShift();

	/**
	 * Gets the lst half day work timezone.
	 *
	 * @return the lst half day work timezone
	 */
	 List<FlexHalfDayWorkTime> getLstHalfDayWorkTimezone();

	/**
	 * Gets the stamp reflect timezone.
	 *
	 * @return the stamp reflect timezone
	 */
	 List<StampReflectTimezone> getLstStampReflectTimezone();

}
