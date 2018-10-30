package nts.uk.ctx.pr.core.dom.monthlysalary.payrolldata;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

import java.math.BigDecimal;

/**
 * 明細金額型
 */
@DecimalRange(min = "-9999999999", max = "9999999999")
@DecimalMantissaMaxLength(0)
public class DetailAmountType extends DecimalPrimitiveValue<DetailAmountType> {
    private static final long serialVersionUID = 1L;

    public DetailAmountType(BigDecimal rawValue) {
        super(rawValue);
    }
}
