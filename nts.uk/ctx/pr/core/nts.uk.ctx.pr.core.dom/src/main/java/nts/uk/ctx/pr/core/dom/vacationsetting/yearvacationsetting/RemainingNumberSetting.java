/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacationsetting.yearvacationsetting;

import lombok.Getter;

/**
 * The Class RemainingNumberSetting.
 */
@Getter
public class RemainingNumberSetting {
    
    /** The remaining day max number. */
    // TODO: Not find Primitive
    private Integer remainingDayMaxNumber;

    /** The retention year. */
    private RetentionYear retentionYear;
}
