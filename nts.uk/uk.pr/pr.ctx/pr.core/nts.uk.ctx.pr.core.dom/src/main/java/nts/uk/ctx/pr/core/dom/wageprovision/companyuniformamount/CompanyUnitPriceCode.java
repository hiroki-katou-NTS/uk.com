package nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
* 会社単価コード
*/
@ZeroPaddedCode
@StringMaxLength(4)
@StringCharType(CharType.NUMERIC)
public class CompanyUnitPriceCode extends CodePrimitiveValue<CompanyUnitPriceCode>
{
    
    private static final long serialVersionUID = 1L;
    
    public CompanyUnitPriceCode(String rawValue)
    {
         super(rawValue);
    }
    
}
