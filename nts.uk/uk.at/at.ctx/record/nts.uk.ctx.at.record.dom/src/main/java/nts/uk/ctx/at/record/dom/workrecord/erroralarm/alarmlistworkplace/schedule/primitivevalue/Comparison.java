package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 職場チェック条件値対比
 *
 * @author Thanh.LNP
 */
@IntegerRange(min = 1, max = 99)
public class Comparison extends IntegerPrimitiveValue<Comparison> {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new amount value.
     *
     * @param rawValue the raw value
     */
    public Comparison(Integer rawValue) {
        super(rawValue);
    }
}
