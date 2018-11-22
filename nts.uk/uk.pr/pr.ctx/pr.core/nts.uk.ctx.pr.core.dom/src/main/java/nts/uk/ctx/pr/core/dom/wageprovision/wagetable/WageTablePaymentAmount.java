package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

import java.math.BigDecimal;

/**
 * 賃金テーブル支給金額
 */
@DecimalRange(min = "0.000", max = "99.999")
@DecimalMantissaMaxLength(3)
public class WageTablePaymentAmount extends DecimalPrimitiveValue<WageTablePaymentAmount> {

    private static final long serialVersionUID = 1L;

    public WageTablePaymentAmount(BigDecimal rawValue) {
        super(rawValue);
    }
}
