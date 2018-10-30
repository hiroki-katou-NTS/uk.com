package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 当該枠上限
 */

@IntegerMaxValue(99)
@IntegerMinValue(0)
public class FrameUpperLimit extends IntegerPrimitiveValue<FrameUpperLimit>
{

    private static final long serialVersionUID = 1L;

    public FrameUpperLimit(int rawValue)
    {
        super(rawValue);
    }

}
