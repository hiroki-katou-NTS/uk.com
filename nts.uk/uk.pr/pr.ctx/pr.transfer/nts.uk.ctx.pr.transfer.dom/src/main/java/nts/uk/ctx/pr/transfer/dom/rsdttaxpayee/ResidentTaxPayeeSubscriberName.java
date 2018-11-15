package nts.uk.ctx.pr.transfer.dom.rsdttaxpayee;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 住民税納付先加入者名
 */
@StringMaxLength(24)
public class ResidentTaxPayeeSubscriberName extends StringPrimitiveValue<ResidentTaxPayeeSubscriberName> {

    private static final long serialVersionUID = 1L;

    public ResidentTaxPayeeSubscriberName(String rawValue) {
        super(rawValue);
    }

}
