package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

import java.math.BigDecimal;

/**
 * 回数値
 */
@DecimalRange(min = "-999.99", max = "999.99")
@DecimalMantissaMaxLength(2)
public class TimesValue extends DecimalPrimitiveValue<TimesValue> {
    private static final long serialVersionUID = 1L;

    public TimesValue(BigDecimal rawValue) {
        super(rawValue);
    }
}
