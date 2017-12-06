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
 * The Interface DiffTimeWorkSettingSetMemento.
 */
public interface DiffTimeWorkSettingSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(String companyId);

    /**
     * Sets the work time code.
     *
     * @param employmentTimezoneCode the new work time code
     */
    public void setWorkTimeCode(WorkTimeCode employmentTimezoneCode);

    /**
     * Sets the rest set.
     *
     * @param restSet the new rest set
     */
    public void setRestSet(FixedWorkRestSet restSet);

    /**
     * Sets the dayoff work timezone.
     *
     * @param dayoffWorkTimezone the new dayoff work timezone
     */
    public void setDayoffWorkTimezone(DiffTimeDayOffWorkTimezone dayoffWorkTimezone);

    /**
     * Sets the common set.
     *
     * @param commonSet the new common set
     */
    public void setCommonSet(WorkTimezoneCommonSet commonSet);

    /**
     * Sets the checks if is use half day shift.
     *
     * @param isUseHalfDayShift the new checks if is use half day shift
     */
    public void setIsUseHalfDayShift(boolean isUseHalfDayShift);

    /**
     * Sets the change extent.
     *
     * @param changeExtent the new change extent
     */
    public void setChangeExtent(EmTimezoneChangeExtent changeExtent);

    /**
     * Sets the half day work timezones.
     *
     * @param halfDayWorkTimezone the new half day work timezones
     */
    public void setHalfDayWorkTimezones(List<DiffTimeHalfDayWorkTimezone> halfDayWorkTimezone);

    /**
     * Sets the stamp reflect timezone.
     *
     * @param stampReflectTimezone the new stamp reflect timezone
     */
    public void setStampReflectTimezone(DiffTimeWorkStampReflectTimezone stampReflectTimezone);

    /**
     * Sets the overtime setting.
     *
     * @param overtimeSetting the new overtime setting
     */
    public void setOvertimeSetting(LegalOTSetting overtimeSetting);
}
