package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 範囲下限
 */

@IntegerMaxValue(99)
@IntegerMinValue(0)
public class RangeLowerLimit extends IntegerPrimitiveValue<RangeLowerLimit>
{

    private static final long serialVersionUID = 1L;

    public RangeLowerLimit(int rawValue)
    {
        super(rawValue);
    }

}
