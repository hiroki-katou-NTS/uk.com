package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * きざみ単位
 */

@IntegerMaxValue(9)
@IntegerMinValue(0)
public class StepIncrement extends IntegerPrimitiveValue<StepIncrement>
{

    private static final long serialVersionUID = 1L;

    public StepIncrement(int rawValue)
    {
        super(rawValue);
    }

}
