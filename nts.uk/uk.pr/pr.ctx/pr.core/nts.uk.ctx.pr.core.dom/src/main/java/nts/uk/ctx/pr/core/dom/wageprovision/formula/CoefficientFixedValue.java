package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

import java.math.BigDecimal;

/**
 * 係数固定値
 */

@DecimalMaxValue("999.99")
@DecimalMinValue("-999.99")
@DecimalMantissaMaxLength(2)
public class CoefficientFixedValue extends DecimalPrimitiveValue<CoefficientFixedValue> {

    private static final long serialVersionUID = 1L;

    public CoefficientFixedValue(BigDecimal rawValue) {
        super(rawValue);
    }

}