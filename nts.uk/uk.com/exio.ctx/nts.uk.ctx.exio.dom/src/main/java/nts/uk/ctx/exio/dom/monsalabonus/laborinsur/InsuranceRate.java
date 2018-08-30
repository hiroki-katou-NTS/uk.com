package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

import java.math.BigDecimal;

/**
 * 保険料率
 */
@DecimalMinValue("0.000")
@DecimalMaxValue("999.999")
public class InsuranceRate extends DecimalPrimitiveValue<PrimitiveValue<BigDecimal>>{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InsuranceRate(BigDecimal rawValue) {
        super(rawValue);
    }

}