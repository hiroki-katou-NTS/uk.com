package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 職場チェック条件値人数
 *
 * @author Thanh.LNP
 */
@IntegerRange(min = 1, max = 99999)
public class NumberOfPeople extends IntegerPrimitiveValue<NumberOfPeople> {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new amount value.
     *
     * @param rawValue the raw value
     */
    public NumberOfPeople(Integer rawValue) {
        super(rawValue);
    }
}
