package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 厚生年金番号
*/
@StringMaxLength(8)
@StringCharType(CharType.NUMERIC)
public class EmpPensionNumber extends StringPrimitiveValue<EmpPensionNumber>
{
    
    private static final long serialVersionUID = 1L;
    
    public EmpPensionNumber(String rawValue)
    {
         super(rawValue);
    }
    
}
