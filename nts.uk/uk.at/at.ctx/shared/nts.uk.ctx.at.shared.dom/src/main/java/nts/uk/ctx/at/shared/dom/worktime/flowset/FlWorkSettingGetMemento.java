/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;

/**
 * The Interface FlowWorkSettingGetMemento.
 */
public interface FlWorkSettingGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();

	/**
	 * Gets the working code.
	 *
	 * @return the working code
	 */
	WorkTimeCode getWorkingCode();

	/**
	 * Gets the rest setting.
	 *
	 * @return the rest setting
	 */
	FlowWorkRestSetting getRestSetting();

	/**
	 * Gets the offday work timezone.
	 *
	 * @return the offday work timezone
	 */
	FlowOffdayWorkTimezone getOffdayWorkTimezone();

	/**
	 * Gets the common setting.
	 *
	 * @return the common setting
	 */
	WorkTimezoneCommonSet getCommonSetting();

	/**
	 * Gets the half day work timezone.
	 *
	 * @return the half day work timezone
	 */
	FlowHalfDayWorkTimezone getHalfDayWorkTimezone();

	/**
	 * Gets the stamp reflect timezone.
	 *
	 * @return the stamp reflect timezone
	 */
	FlowStampReflectTimezone getStampReflectTimezone();

	/**
	 * Gets the designated setting.
	 *
	 * @return the designated setting
	 */
	LegalOTSetting getLegalOTSetting();

	/**
	 * Gets the flow setting.
	 *
	 * @return the flow setting
	 */
	FlowWorkDedicateSetting getFlowSetting();
}
