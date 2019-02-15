package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.LongPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;
import nts.arc.primitive.constraint.LongMaxValue;

import java.math.BigDecimal;

@DecimalMaxValue("9999999999")
@DecimalMinValue("-9999999999")
@DecimalMantissaMaxLength(0)
public class PaymentAndDeductionItem extends DecimalPrimitiveValue<PaymentAndDeductionItem> {

    public PaymentAndDeductionItem(BigDecimal rawValue) {
        super(rawValue);
    }
}
