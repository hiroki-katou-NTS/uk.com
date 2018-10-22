package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 *  住所１
 */
@StringMaxLength(120)
public class Address1 extends StringPrimitiveValue<Address1> {
    private static final long serialVersionUID = 1L;

    public Address1(String rawValue)
    {
        super(rawValue);
    }
}
