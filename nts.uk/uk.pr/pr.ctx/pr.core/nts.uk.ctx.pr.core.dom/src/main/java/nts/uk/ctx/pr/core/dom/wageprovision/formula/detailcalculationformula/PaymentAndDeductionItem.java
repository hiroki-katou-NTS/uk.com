package nts.uk.ctx.pr.core.dom.wageprovision.formula.detailcalculationformula;

import nts.arc.primitive.LongPrimitiveValue;
import nts.arc.primitive.constraint.LongMaxValue;
import nts.arc.primitive.constraint.LongMinValue;

@LongMaxValue(9999999999L)
@LongMinValue(-9999999999L)
public class PaymentAndDeductionItem extends LongPrimitiveValue<PaymentAndDeductionItem> {

    public PaymentAndDeductionItem(Long rawValue) {
        super(rawValue);
    }
}
