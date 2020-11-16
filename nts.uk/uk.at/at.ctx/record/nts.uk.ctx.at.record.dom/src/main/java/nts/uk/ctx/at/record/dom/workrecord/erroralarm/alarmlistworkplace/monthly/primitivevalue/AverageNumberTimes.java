package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 平均回数
 *
 * @author Thanh.LNP
 */
@IntegerRange(min = 1, max = 99)
public class AverageNumberTimes extends IntegerPrimitiveValue<AverageNumberTimes> {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new amount value.
     *
     * @param rawValue the raw value
     */
    public AverageNumberTimes(Integer rawValue) {
        super(rawValue);
    }
}
