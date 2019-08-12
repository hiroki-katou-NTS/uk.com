package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 健康保険番号
 */

/**/
@StringMaxLength(3)
@StringCharType(CharType.NUMERIC)
public class FdNumber extends StringPrimitiveValue<FdNumber> {
    private static final long serialVersionUID = 1L;

    public FdNumber(String rawValue)
    {
        super(rawValue);
    }
}
