package nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.lifeInsurance;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 生命保険コード
*/
@StringMaxLength(2)
@ZeroPaddedCode
@StringCharType(CharType.NUMERIC)
public class LifeInsuranceCode extends StringPrimitiveValue<LifeInsuranceCode>
{
    
    private static final long serialVersionUID = 1L;
    
    public LifeInsuranceCode(String rawValue)
    {
         super(rawValue);
    }
    
}
