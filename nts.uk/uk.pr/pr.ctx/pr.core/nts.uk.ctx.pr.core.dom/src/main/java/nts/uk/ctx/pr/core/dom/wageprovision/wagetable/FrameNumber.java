package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 枠番
 */

@IntegerMaxValue(99)
@IntegerMinValue(0)
public class FrameNumber extends IntegerPrimitiveValue<FrameNumber>
{

    private static final long serialVersionUID = 1L;

    public FrameNumber(int rawValue)
    {
        super(rawValue);
    }

}
