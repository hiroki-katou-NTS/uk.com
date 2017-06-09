/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import java.util.List;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

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
    void setStartMonthDay(Integer startMonthDay);

    /**
     * Sets the max person setting.
     *
     * @param maxPersonSetting the new max person setting
     */
    void setMaxPersonSetting(MaxPersonSetting maxPersonSetting);

    /**
     * Sets the work type codes.
     *
     * @param workTypeCodes the new work type codes
     */
    void setWorkTypeCodes(List<String> workTypeCodes);
}
