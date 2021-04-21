package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 職場チェック条件値金額
 *
 * @author Thanh.LNP
 */
@IntegerRange(min = 1, max = 9999999)
public class Amount extends IntegerPrimitiveValue<Amount> {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new amount value.
     *
     * @param rawValue the raw value
     */
    public Amount(Integer rawValue) {
        super(rawValue);
    }
}
