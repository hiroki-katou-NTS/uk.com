package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 住所1
 */
@StringMaxLength(120)
public class AddressKana1 extends StringPrimitiveValue<AddressKana1> {
    private static final long serialVersionUID = 1L;

    public AddressKana1(String rawValue) {
        super(rawValue);
    }
}
