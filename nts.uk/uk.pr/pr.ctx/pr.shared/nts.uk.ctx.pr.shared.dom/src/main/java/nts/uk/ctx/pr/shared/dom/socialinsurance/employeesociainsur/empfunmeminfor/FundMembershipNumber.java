package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 基金加入員番号
*/
@StringMaxLength(11)
@StringCharType(CharType.NUMERIC)
public class FundMembershipNumber extends StringPrimitiveValue<FundMembershipNumber>
{
    
    private static final long serialVersionUID = 1L;
    
    public FundMembershipNumber(String rawValue)
    {
         super(rawValue);
    }
    
}
