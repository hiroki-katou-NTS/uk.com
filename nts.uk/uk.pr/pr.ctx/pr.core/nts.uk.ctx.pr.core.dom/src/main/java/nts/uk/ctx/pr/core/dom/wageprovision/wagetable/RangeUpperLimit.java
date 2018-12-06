package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 範囲上限
 */

@IntegerMaxValue(99)
@IntegerMinValue(0)
public class RangeUpperLimit extends IntegerPrimitiveValue<RangeUpperLimit>
{

    private static final long serialVersionUID = 1L;

    public RangeUpperLimit(int rawValue)
    {
        super(rawValue);
    }

}
