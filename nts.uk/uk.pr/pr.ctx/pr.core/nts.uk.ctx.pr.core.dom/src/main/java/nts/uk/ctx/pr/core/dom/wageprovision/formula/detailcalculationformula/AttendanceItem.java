package nts.uk.ctx.pr.core.dom.wageprovision.formula.detailcalculationformula;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

import java.math.BigDecimal;

@DecimalMaxValue("99999.99")
@DecimalMinValue("-99999.99")
@DecimalMantissaMaxLength(2)
public class AttendanceItem extends DecimalPrimitiveValue<AttendanceItem> {

    public AttendanceItem(BigDecimal rawValue) {
        super(rawValue);
    }
}
