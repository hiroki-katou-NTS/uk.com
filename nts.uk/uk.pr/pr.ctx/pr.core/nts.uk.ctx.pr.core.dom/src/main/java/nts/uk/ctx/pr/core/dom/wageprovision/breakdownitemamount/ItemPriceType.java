package nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount;

import nts.arc.primitive.LongPrimitiveValue;
import nts.arc.primitive.constraint.LongMaxValue;
import nts.arc.primitive.constraint.LongMinValue;

@LongMinValue(-9999999999L)
@LongMaxValue(9999999999L)
public class ItemPriceType extends LongPrimitiveValue<ItemPriceType> {
    private static final long serialVersionUID = 1L;

    /**
     * @param rawValue
     */
    public ItemPriceType(long rawValue) {
        super(rawValue);
    }

}
