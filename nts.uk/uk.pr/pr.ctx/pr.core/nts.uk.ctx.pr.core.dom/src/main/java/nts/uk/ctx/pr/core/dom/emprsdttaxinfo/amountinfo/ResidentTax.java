package nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;

import java.math.BigDecimal;

@DecimalRange(min = "0", max = "9999999999")
public class ResidentTax extends DecimalPrimitiveValue<ResidentTax> {

    private static final long serialVersionUID = 1L;

    public ResidentTax(BigDecimal rawValue) {
        super(rawValue);
    }

}
