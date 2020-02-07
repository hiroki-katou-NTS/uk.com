package nts.uk.ctx.pr.core.dom.socialinsurance;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

import java.math.BigDecimal;

/**
 * 保険料率
 */
@DecimalRange(min = "0.000", max = "999.999")
@DecimalMantissaMaxLength(3)
public class InsuranceRate extends DecimalPrimitiveValue<InsuranceRate> {

    private static final long serialVersionUID = 1L;

    public InsuranceRate(BigDecimal rawValue) {
        super(rawValue);
    }
}
