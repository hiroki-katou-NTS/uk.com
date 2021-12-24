/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeAnnualRoundProcesCla;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;

/**
 * The Interface TimeVacationSettingSetMemento.
 */
public interface TimeAnnualSettingSetMemento {

    /**
     * Sets the company id.
     *
     * @param companyId the new company id
     */
    void setCompanyId(String companyId);

    /**
     * Sets the time manage type.
     *
     * @param timeManageType the new time manage type
     */
    void setTimeManageType(ManageDistinct timeManageType);

    void setTimeUnit(TimeDigestiveUnit timeUnit);

    /**
     * Sets the max year day leave.
     *
     * @param maxYearDayLeave the new max year day leave
     */
    void setMaxYearDayLeave(TimeAnnualMaxDay maxYearDayLeave);

//    /**
//     * Sets the enough time one day.
//     *
//     * @param isEnoughTimeOneDay the new enough time one day
//     */
//    void setEnoughTimeOneDay(boolean isEnoughTimeOneDay);

    /**
     * Sets TimeAnnualRoundProcesCla.
     *
     * @param timeAnnualRoundProcesCla
     */
    void setRoundProcessClassific(TimeAnnualRoundProcesCla timeAnnualRoundProcesCla);
    
    void setTimeAnnualLeaveTimeDay(TimeAnnualLeaveTimeDay timeAnnualLeaveTimeDay);
    
    void setTimeVacationDigestUnit(TimeVacationDigestUnit timeVacationDigestUnit);
}
