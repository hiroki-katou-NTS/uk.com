package nts.uk.ctx.pr.core.dom.wageprovision.formula.detailcalculationformula;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

import java.math.BigDecimal;

@DecimalMaxValue("9999999999.99")
@DecimalMinValue("-9999999999.99")
@DecimalMantissaMaxLength(2)
public class CompanyAndIndividualUnitPrice extends DecimalPrimitiveValue<CompanyAndIndividualUnitPrice> {

    public CompanyAndIndividualUnitPrice(BigDecimal rawValue) {
        super(rawValue);
    }
}
