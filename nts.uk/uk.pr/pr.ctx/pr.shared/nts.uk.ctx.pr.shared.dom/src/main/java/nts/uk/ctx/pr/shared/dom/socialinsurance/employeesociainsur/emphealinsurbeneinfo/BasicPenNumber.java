package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 基礎年金番号
*/
@StringMaxLength(10)
@StringCharType(CharType.NUMERIC)
public class BasicPenNumber extends StringPrimitiveValue<BasicPenNumber>
{
    
    private static final long serialVersionUID = 1L;
    
    public BasicPenNumber(String rawValue)
    {
         super(rawValue);
    }
    
}
