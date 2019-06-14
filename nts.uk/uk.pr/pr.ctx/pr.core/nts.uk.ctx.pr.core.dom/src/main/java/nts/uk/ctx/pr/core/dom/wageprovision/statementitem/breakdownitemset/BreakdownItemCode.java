package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 
 * @author thanh.tq 内訳項目コード
 *
 */
@StringMaxLength(2)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class BreakdownItemCode extends CodePrimitiveValue<BreakdownItemCode>
{
    
    private static final long serialVersionUID = 1L;
    
    public BreakdownItemCode(String rawValue)
    {
         super(rawValue);
    }
    
}
