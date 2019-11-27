package nts.uk.ctx.pr.transfer.dom.rsdttaxpayee;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 住民税納付先名
 */
@StringMaxLength(24)
public class ResidentTaxPayeeName extends StringPrimitiveValue<ResidentTaxPayeeName> {

    private static final long serialVersionUID = 1L;

    public ResidentTaxPayeeName(String rawValue) {
        super(rawValue);
    }

}
