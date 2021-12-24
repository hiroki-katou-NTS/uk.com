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
 * The Interface TimeVacationSettingGetMemento.
 */
public interface TimeAnnualSettingGetMemento {

    /**
     * Gets the company id.
     *
     * @return the company id
     */
    String getCompanyId();

    /**
     * Gets the time manage type.
     *
     * @return the time manage type
     */
    ManageDistinct getTimeManageType();

    /**
     * Gets the time unit.
     *
     * @return the time unit
     */
    TimeDigestiveUnit getTimeUnit();

    /**
     * Gets the max year day leave.
     *
     * @return the max year day leave
     */
    TimeAnnualMaxDay getMaxYearDayLeave();

    // 要確認1224
//    /**
//     * Checks if is enough time one day.
//     *
//     * @return true, if is enough time one day
//     */
//    boolean isEnoughTimeOneDay();

    /**
     * Checks RoundProcessClassific.
     *
     * @return RoundProcessClassific
     */
    TimeAnnualRoundProcesCla GetRoundProcessClassific();
    
    TimeAnnualLeaveTimeDay getTimeAnnualLeaveTimeDay();
    
    TimeVacationDigestUnit getTimeVacationDigestUnit();
    
}
