package nts.uk.ctx.pr.core.dom.laborinsurance;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

import java.math.BigDecimal;

/**
 * 保険料率
 */
@DecimalMinValue("0")
@DecimalMaxValue("999.999")
@DecimalMantissaMaxLength(3)
public class InsuranceRate extends DecimalPrimitiveValue<InsuranceRate>{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InsuranceRate(BigDecimal rawValue) {
        super(rawValue);
    }

}