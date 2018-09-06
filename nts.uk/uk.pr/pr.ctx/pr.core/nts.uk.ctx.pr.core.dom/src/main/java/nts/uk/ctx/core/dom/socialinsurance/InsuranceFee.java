package nts.uk.ctx.core.dom.socialinsurance;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

import java.math.BigDecimal;

/**
 * 保険料
 */
@DecimalRange(min = "0.00", max = "9999999999.99")
@DecimalMantissaMaxLength(2)
public class InsuranceFee extends DecimalPrimitiveValue<InsuranceFee> {

    private static final long serialVersionUID = 1L;

    public InsuranceFee(BigDecimal rawValue) {
        super(rawValue);
    }
}
