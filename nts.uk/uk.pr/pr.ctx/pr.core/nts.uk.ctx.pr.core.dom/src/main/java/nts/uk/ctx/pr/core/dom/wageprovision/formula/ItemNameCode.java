package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 
 * @author thanh.tq 項目名コード
 *
 */
@StringMaxLength(4)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode

public class ItemNameCode extends CodePrimitiveValue<ItemNameCode>
{
    
    private static final long serialVersionUID = 1L;
    
    public ItemNameCode(String rawValue)
    {
         super(rawValue);
    }
    
}