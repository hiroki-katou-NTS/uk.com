/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class NumberDayNursing.
 * 子の看護介護上限日数
 */
@IntegerRange(max = 99, min = 0)
public class NursingNumberLeaveDay extends IntegerPrimitiveValue<NursingNumberLeaveDay> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Instantiates a new number day nursing.
     *
     * @param rawValue the raw value
     */
    public NursingNumberLeaveDay(Integer rawValue) {
        super(rawValue);
    }
}
