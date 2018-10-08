package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author thanh.tq 内訳項目コード
 *
 */
@StringMaxLength(2)
@StringCharType(CharType.NUMERIC)
public class BreakdownItemCode extends StringPrimitiveValue<BreakdownItemCode>
{
    
    private static final long serialVersionUID = 1L;
    
    public BreakdownItemCode(String rawValue)
    {
         super(rawValue);
    }
    
}
