package nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract;

import nts.arc.primitive.LongPrimitiveValue;
import nts.arc.primitive.constraint.LongMaxValue;
import nts.arc.primitive.constraint.LongMinValue;

@LongMinValue(-9999999999L)
@LongMaxValue(9999999999L)
public class AmountOfMoney extends LongPrimitiveValue<AmountOfMoney> {
    private static final long serialVersionUID = 1L;

    /**
     * @param rawValue
     */
    public AmountOfMoney(long rawValue) {
        super(rawValue);
    }

}
