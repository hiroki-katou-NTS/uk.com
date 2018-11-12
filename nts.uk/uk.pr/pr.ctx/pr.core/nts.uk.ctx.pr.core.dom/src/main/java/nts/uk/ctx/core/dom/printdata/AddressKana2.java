package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 *  住所カナ２
 */
@StringMaxLength(40)
public class AddressKana2 extends StringPrimitiveValue<AddressKana2> {
    private static final long serialVersionUID = 1L;

    public AddressKana2(String rawValue)
    {
        super(rawValue);
    }
}
