package nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.StringPrimitiveValue;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
* 個人金額コード
*/
@StringMaxLength(2)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class IndividualPriceCode extends CodePrimitiveValue<IndividualPriceCode>
{
    
    private static final long serialVersionUID = 1L;
    
    public IndividualPriceCode(String rawValue)
    {
         super(rawValue);
    }
    
}
