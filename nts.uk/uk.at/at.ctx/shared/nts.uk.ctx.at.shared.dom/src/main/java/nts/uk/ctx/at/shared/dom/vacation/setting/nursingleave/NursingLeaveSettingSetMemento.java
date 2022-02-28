/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.shr.com.time.calendar.MonthDay;

/**
 * The Interface NursingVacationSettingSetMemento.
 */
public interface NursingLeaveSettingSetMemento {

    /**
     * Sets the company id.
     *
     * @param companyId the new company id
     */
    void setCompanyId(String companyId);

    /**
     * Sets the manage type.
     *
     * @param manageType the new manage type
     */
    void setManageType(ManageDistinct manageType);

    /**
     * Sets the nursing category.
     *
     * @param nursingCategory the new nursing category
     */
    void setNursingCategory(NursingCategory nursingCategory);

    /**
     * Sets the start month day.
     *
     * @param startMonthDay the new start month day
     */
    void setStartMonthDay(MonthDay startMonthDay);

    /**
     * Sets the max person setting.
     *
     * @param maxPersonSetting the new max person setting
     */
    void setMaxPersonSetting(List<MaxPersonSetting> maxPersonSetting);

    /**
     * Sets the special holiday frame.
     *
     * @param specialHolidayFrame the new special holiday frame
     */
	void setHdspFrameNo(Optional<Integer> hdspFrameNo);

    /**
     * Sets the work absence.
     *
     * @param workAbsence the new work absence
     */
    void setAbsenceFrameNo(Optional<Integer> absenceFrameNo);



    void setTimeVacationDigestUnit(TimeVacationDigestUnit timeVacationDigestUnit);

    void setNumPer1(Integer numPer1);

    void setNumPer2(Integer numPer2);
}
