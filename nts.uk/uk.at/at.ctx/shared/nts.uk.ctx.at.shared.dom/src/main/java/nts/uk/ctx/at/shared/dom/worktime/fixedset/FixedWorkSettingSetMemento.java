/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;

/**
 * The Interface FixedWorkSettingSetMemento.
 */
public interface FixedWorkSettingSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(String companyId);

	/**
	 * Sets the work time code.
	 *
	 * @param workTimeCode the new work time code
	 */
	void setWorkTimeCode(WorkTimeCode workTimeCode);

	/**
	 * Sets the offday work timezone.
	 *
	 * @param offdayWorkTimezone the new offday work timezone
	 */
	void setOffdayWorkTimezone(FixOffdayWorkTimezone offdayWorkTimezone);

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
	void setUseHalfDayShift(Boolean useHalfDayShift);

	/**
	 * Sets the fixed work rest setting.
	 *
	 * @param fixedWorkRestSetting the new fixed work rest setting
	 */
	void setFixedWorkRestSetting(FixedWorkRestSet fixedWorkRestSetting);

	/**
	 * Sets the lst half day work timezone.
	 *
	 * @param lstHalfDayWorkTimezone the new lst half day work timezone
	 */
	void setLstHalfDayWorkTimezone(List<FixHalfDayWorkTimezone> lstHalfDayWorkTimezone);

	/**
	 * Sets the lst stamp reflect timezone.
	 *
	 * @param lstStampReflectTimezone the new lst stamp reflect timezone
	 */
	void setLstStampReflectTimezone(List<StampReflectTimezone> lstStampReflectTimezone);

	/**
	 * Sets the legal OT setting.
	 *
	 * @param legalOTSetting the new legal OT setting
	 */
	void setLegalOTSetting(LegalOTSetting legalOTSetting);

	/**
	 * Sets the calculation setting.
	 *
	 * @param fixedWorkCalcSetting the new calculation setting
	 */
	void setCalculationSetting(Optional<FixedWorkCalcSetting> fixedWorkCalcSetting);
}
