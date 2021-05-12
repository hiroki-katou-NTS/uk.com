package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 職場比率対比
 *
 * @author Thanh.LNP
 */
@IntegerRange(min = 1, max = 999)
public class RatioComparison extends IntegerPrimitiveValue<RatioComparison> {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new amount value.
     *
     * @param rawValue the raw value
     */
    public RatioComparison(Integer rawValue) {
        super(rawValue);
    }
}
