package nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 保険種類コード
*/
@StringMaxLength(2)
@ZeroPaddedCode
@StringCharType(CharType.NUMERIC)
public class InsuranceTypeCode extends StringPrimitiveValue<InsuranceTypeCode>
{
    
    private static final long serialVersionUID = 1L;
    
    public InsuranceTypeCode(String rawValue)
    {
         super(rawValue);
    }
    
}
