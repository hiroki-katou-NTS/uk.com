package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 *  住所カナ１
 */
@StringMaxLength(120)
public class AddressKana1 extends StringPrimitiveValue<AddressKana1> {
    private static final long serialVersionUID = 1L;

    public AddressKana1(String rawValue)
    {
        super(rawValue);
    }
}
