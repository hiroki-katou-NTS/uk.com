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
public interface FlexWorkSettingSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setcompanyId(String companyId);

	/**
	 * Sets the work time code.
	 *
	 * @param workTimeCode the new work time code
	 */
	void setWorkTimeCode(WorkTimeCode workTimeCode);

	/**
	 * Sets the core time setting.
	 *
	 * @param coreTimeSetting the new core time setting
	 */
	void setCoreTimeSetting(CoreTimeSetting coreTimeSetting);

	/**
	 * Sets the rest setting.
	 *
	 * @param restSetting the new rest setting
	 */
	void setRestSetting(FlowWorkRestSetting restSetting);

	/**
	 * Sets the offday work time.
	 *
	 * @param offdayWorkTime the new offday work time
	 */
	void setOffdayWorkTime(FlexOffdayWorkTime offdayWorkTime);

	/**
	 * Sets the common setting.
	 *
	 * @param commonSetting the new common setting
	 */
	void setCommonSetting(WorkTimezoneCommonSet commonSetting);

	/**
	 * Sets the use half day shift.
	 *
	 * @param useHalfDayShift the new use half day shift
	 */
	void setUseHalfDayShift(HalfDayWorkSet useHalfDayShift);

	/**
	 * Sets the lst half day work timezone.
	 *
	 * @param halfDayWorkTimezone the new lst half day work timezone
	 */
	void setLstHalfDayWorkTimezone(List<FlexHalfDayWorkTime> halfDayWorkTimezone);

	/**
	 * Sets the lst stamp reflect timezone.
	 *
	 * @param stampReflectTimezone the new lst stamp reflect timezone
	 */
	void setLstStampReflectTimezone(List<StampReflectTimezone> stampReflectTimezone);

}
