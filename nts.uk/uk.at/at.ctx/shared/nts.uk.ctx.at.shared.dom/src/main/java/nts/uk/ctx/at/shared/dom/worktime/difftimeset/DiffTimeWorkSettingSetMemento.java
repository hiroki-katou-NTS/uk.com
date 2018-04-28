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
 * The Interface DiffTimeWorkSettingSetMemento.
 */
public interface DiffTimeWorkSettingSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(String companyId);

	/**
     * Sets the work time code.
     *
     * @param employmentTimezoneCode the new work time code
     */
    void setWorkTimeCode(WorkTimeCode employmentTimezoneCode);

	/**
     * Sets the rest set.
     *
     * @param restSet the new rest set
     */
    void setRestSet(FixedWorkRestSet restSet);

	/**
     * Sets the dayoff work timezone.
     *
     * @param dayoffWorkTimezone the new dayoff work timezone
     */
    void setDayoffWorkTimezone(DiffTimeDayOffWorkTimezone dayoffWorkTimezone);

	/**
     * Sets the common set.
     *
     * @param commonSet the new common set
     */
    void setCommonSet(WorkTimezoneCommonSet commonSet);

	/**
     * Sets the checks if is use half day shift.
     *
     * @param isUseHalfDayShift the new checks if is use half day shift
     */
    void setIsUseHalfDayShift(boolean isUseHalfDayShift);

	/**
     * Sets the change extent.
     *
     * @param changeExtent the new change extent
     */
    void setChangeExtent(EmTimezoneChangeExtent changeExtent);

	/**
     * Sets the half day work timezones.
     *
     * @param halfDayWorkTimezone the new half day work timezones
     */
    void setHalfDayWorkTimezones(List<DiffTimeHalfDayWorkTimezone> halfDayWorkTimezone);

	/**
     * Sets the stamp reflect timezone.
     *
     * @param stampReflectTimezone the new stamp reflect timezone
     */
    void setStampReflectTimezone(DiffTimeWorkStampReflectTimezone stampReflectTimezone);

	/**
     * Sets the overtime setting.
     *
     * @param overtimeSetting the new overtime setting
     */
    void setOvertimeSetting(LegalOTSetting overtimeSetting);

	/**
     * Sets the calculation setting.
     *
     * @param fixedWorkCalcSetting the new calculation setting
     */
    void setCalculationSetting(Optional<FixedWorkCalcSetting> fixedWorkCalcSetting);
}
