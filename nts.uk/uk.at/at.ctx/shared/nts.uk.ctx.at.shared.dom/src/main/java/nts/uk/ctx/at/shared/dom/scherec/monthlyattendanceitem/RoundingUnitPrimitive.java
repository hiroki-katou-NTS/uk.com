package nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * @author anhnm
 * 丸め単位
 *
 */
@IntegerRange(min = 0, max = 100000)
public class RoundingUnitPrimitive extends IntegerPrimitiveValue<RoundingUnitPrimitive> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public RoundingUnitPrimitive(Integer rawValue) {
        super(rawValue);
    }

}
