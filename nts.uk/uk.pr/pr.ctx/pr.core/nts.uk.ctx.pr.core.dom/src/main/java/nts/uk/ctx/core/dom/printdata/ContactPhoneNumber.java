package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 *  連絡者電話番号
 */
@StringMaxLength(20)
public class ContactPhoneNumber extends StringPrimitiveValue<ContactPhoneNumber> {
    private static final long serialVersionUID = 1L;

    public ContactPhoneNumber(String rawValue)
    {
        super(rawValue);
    }
}
