package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 *  会計事務所電話番号
 */
@StringMaxLength(20)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class AccountingOffTelePhoneNumber extends StringPrimitiveValue<AccountingOffTelePhoneNumber> {
    private static final long serialVersionUID = 1L;

    public AccountingOffTelePhoneNumber(String rawValue)
    {
        super(rawValue);
    }
}
