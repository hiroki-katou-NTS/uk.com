/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacationsetting.yearvacationsetting;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.vacation.setting.ManageDistinct;

/**
 * The Class YearVacationTimeMaxDay.
 */
@Getter
public class YearVacationTimeMaxDay {
    
    /** The time manage. */
    // TODO: not find 時間年休上限日数
    private int timeManage;
    
    /** The manage type. */
    private ManageDistinct manageType;
    
    /** The reference. */
    private MaxDayReference reference;
}
