package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
* 計算式名
*/
@StringMaxLength(20)
public class FormulaName extends StringPrimitiveValue<FormulaName>
{
    
    private static final long serialVersionUID = 1L;
    
    public FormulaName(String rawValue)
    {
         super(rawValue);
    }
    
}
