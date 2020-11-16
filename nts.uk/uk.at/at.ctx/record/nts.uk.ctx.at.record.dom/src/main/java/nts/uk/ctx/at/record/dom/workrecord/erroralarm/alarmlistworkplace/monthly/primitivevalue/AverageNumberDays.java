package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;

import java.math.BigDecimal;

/**
 * 平均日数
 *
 * @author Thanh.LNP
 */
@DecimalRange(min = "0.1", max = "99.9")
public class AverageNumberDays extends DecimalPrimitiveValue<AverageNumberDays> {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new amount value.
     *
     * @param rawValue the raw value
     */
    public AverageNumberDays(BigDecimal rawValue) {
        super(rawValue);
    }
}
