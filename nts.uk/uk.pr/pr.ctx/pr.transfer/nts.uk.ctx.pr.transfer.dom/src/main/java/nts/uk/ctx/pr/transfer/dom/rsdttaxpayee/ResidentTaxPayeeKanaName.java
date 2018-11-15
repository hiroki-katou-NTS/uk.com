package nts.uk.ctx.pr.transfer.dom.rsdttaxpayee;

import nts.arc.primitive.KanaPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 住民税納付先カナ名
 */
@StringMaxLength(15)
public class ResidentTaxPayeeKanaName extends KanaPrimitiveValue<ResidentTaxPayeeKanaName> {

    private static final long serialVersionUID = 1L;

    public ResidentTaxPayeeKanaName(String rawValue) {
        super(rawValue);
    }

}
