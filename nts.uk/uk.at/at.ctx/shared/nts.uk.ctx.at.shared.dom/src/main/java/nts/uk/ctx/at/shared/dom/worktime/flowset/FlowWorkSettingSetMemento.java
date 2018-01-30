/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;

/**
 * The Interface FlowWorkSettingSetMemento.
 */
public interface FlowWorkSettingSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param cid the new company id
	 */
	void setCompanyId(String cid);

	/**
	 * Sets the working code.
	 *
	 * @param wtCode the new working code
	 */
	void setWorkingCode(WorkTimeCode wtCode);

	/**
	 * Sets the rest setting.
	 *
	 * @param restSet the new rest setting
	 */
	void setRestSetting(FlowWorkRestSetting restSet);

	/**
	 * Sets the offday work timezone.
	 *
	 * @param offDayWtz the new offday work timezone
	 */
	void setOffdayWorkTimezone(FlowOffdayWorkTimezone offDayWtz);

	/**
	 * Sets the common setting.
	 *
	 * @param cmnSet the new common setting
	 */
	void setCommonSetting(WorkTimezoneCommonSet cmnSet);

	/**
	 * Sets the half day work timezone.
	 *
	 * @param halfDayWtz the new half day work timezone
	 */
	void setHalfDayWorkTimezone(FlowHalfDayWorkTimezone halfDayWtz);

	/**
	 * Sets the stamp reflect timezone.
	 *
	 * @param stampRefTz the new stamp reflect timezone
	 */
	void setStampReflectTimezone(FlowStampReflectTimezone stampRefTz);

	/**
	 * Sets the designated setting.
	 *
	 * @param legalOtSet the new designated setting
	 */
	void setLegalOTSetting(LegalOTSetting legalOtSet);

	/**
	 * Sets the flow setting.
	 *
	 * @param flowSet the new flow setting
	 */
	void setFlowSetting(FlowWorkDedicateSetting flowSet);
}
