/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.vacation.setting.ManageDistinct;

/**
 * The Class RemainingNumberSetting.
 */
@Setter
@Getter
public class RemainingNumberSetting {
    
    /** The work day calculate. */
    private ManageDistinct workDayCalculate;
    
    /** The half day manage. */
    private HalfDayManage halfDayManage;
    
    /** The maximum day vacation. */
    private YearVacationAge maximumDayVacation;
    
    /** The remaining day max number. */
    // TODO: Not find Primitive 残数上限日数
    private Integer remainingDayMaxNumber;

    /** The retention year. */
    private RetentionYear retentionYear;
}
