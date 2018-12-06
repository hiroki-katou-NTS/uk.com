package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 項目名コード
 */
@StringMaxLength(4)
@StringCharType(CharType.NUMERIC)
public class ItemNameCode extends StringPrimitiveValue<ItemNameCode>
{
    
    private static final long serialVersionUID = 1L;
    
    public ItemNameCode(String rawValue)
    {
         super(rawValue);
    }
    
}