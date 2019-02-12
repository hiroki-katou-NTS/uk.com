package nts.uk.ctx.pr.shared.dom.salgenpurposeparam;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

import java.math.BigDecimal;

/**
* パラメータ数値
*/
@DecimalMaxValue("999999999.99")
@DecimalMinValue("-999999999.99")
@DecimalMantissaMaxLength(2)
public class ParamNumber extends DecimalPrimitiveValue<ParamNumber> {
    
    private static final long serialVersionUID = 1L;
    
    public ParamNumber(BigDecimal rawValue)
    {
         super(rawValue);
    }
    
}
