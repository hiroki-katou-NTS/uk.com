package nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * @author anhnm
 * 丸め単位
 *
 */
@DecimalRange(min = "0.5", max = "100000")
public class RoundingUnitPrimitive extends DecimalPrimitiveValue<RoundingUnitPrimitive> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public RoundingUnitPrimitive(BigDecimal rawValue) {
        super(rawValue);
    }

}
