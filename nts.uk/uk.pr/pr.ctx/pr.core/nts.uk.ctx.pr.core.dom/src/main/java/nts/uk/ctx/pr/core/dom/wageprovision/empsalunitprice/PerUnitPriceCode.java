package nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
* 個人単価コード
*/
@StringMaxLength(4)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class PerUnitPriceCode extends CodePrimitiveValue<PerUnitPriceCode>
{
    
    private static final long serialVersionUID = 1L;
    
    public PerUnitPriceCode(String rawValue)
    {
         super(rawValue);
    }
    
}
