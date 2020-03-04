package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 賃金月額
 */
@IntegerMinValue(0)
@IntegerMaxValue(9999)
public class PayWage extends IntegerPrimitiveValue<PayWage> {

    public PayWage(Integer rawValue) {
        super(rawValue);
    }
}
