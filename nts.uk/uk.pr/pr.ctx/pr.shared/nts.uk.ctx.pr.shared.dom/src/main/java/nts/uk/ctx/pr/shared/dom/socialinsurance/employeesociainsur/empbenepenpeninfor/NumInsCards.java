package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 保険証回収枚数
 */
@IntegerMaxValue(99)
@IntegerMinValue(0)
public class NumInsCards extends IntegerPrimitiveValue<NumInsCards> {

    private static final long serialVersionUID = 1L;

    public NumInsCards(int rawValue) {
        super(rawValue);
    }

}
