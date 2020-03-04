package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.primitive.LongPrimitiveValue;
import nts.arc.primitive.constraint.LongMaxValue;
import nts.arc.primitive.constraint.LongMinValue;

/**
 * 固定金額
 */

@LongMaxValue(9999999999L)
@LongMinValue(-9999999999L)
public class FixedAmount extends LongPrimitiveValue<FixedAmount> {

    private static final long serialVersionUID = 1L;

    public FixedAmount(Long rawValue) {
        super(rawValue);
    }

}
