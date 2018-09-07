package nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 住所1
 */
@StringMaxLength(120)
public class Address1 extends StringPrimitiveValue<Address1> {
    private static final long serialVersionUID = 1L;

    public Address1(String rawValue) {
        super(rawValue);
    }
}
