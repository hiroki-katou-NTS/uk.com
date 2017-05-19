/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacationsetting.yearvacationsetting;

import lombok.Getter;

/**
 * The Class YearVacationManageSetting.
 */
@Getter
public class YearVacationManageSetting {
    
    /** The remaining number setting. */
    private RemainingNumberSetting remainingNumberSetting;

    /** The acquisition setting. */
    private AcquisitionVacationSetting acquisitionSetting;

    /** The work day calculate. */
    private boolean workDayCalculate;

    /** The half day manage. */
    private HalfDayManage halfDayManage;

    /** The display setting. */
    private DisplaySetting displaySetting;

    /** The maximum day vacation. */
    private YearVacationAge maximumDayVacation;
}
