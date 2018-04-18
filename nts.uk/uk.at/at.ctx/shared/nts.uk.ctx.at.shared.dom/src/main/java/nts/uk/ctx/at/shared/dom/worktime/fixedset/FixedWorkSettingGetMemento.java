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
 * The Interface FixedWorkSettingGetMemento.
 */
public interface FixedWorkSettingGetMemento {

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
	 * Gets the offday work timezone.
	 *
	 * @return the offday work timezone
	 */
	FixOffdayWorkTimezone getOffdayWorkTimezone();

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
	Boolean getUseHalfDayShift();

	/**
	 * Gets the fixed work rest setting.
	 *
	 * @return the fixed work rest setting
	 */
	FixedWorkRestSet getFixedWorkRestSetting();

	/**
	 * Gets the lst half day work timezone.
	 *
	 * @return the lst half day work timezone
	 */
	List<FixHalfDayWorkTimezone> getLstHalfDayWorkTimezone();

	/**
	 * Gets the lst stamp reflect timezone.
	 *
	 * @return the lst stamp reflect timezone
	 */
	List<StampReflectTimezone> getLstStampReflectTimezone();

	/**
	 * Gets the legal OT setting.
	 *
	 * @return the legal OT setting
	 */
	LegalOTSetting getLegalOTSetting();

	/**
	 * Gets the calculation setting.
	 *
	 * @return the calculation setting
	 */
	Optional<FixedWorkCalcSetting> getCalculationSetting();

}
