package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* パラメータ文字
*/
@StringMaxLength(20)
public class ParamCharacter extends StringPrimitiveValue<ParamCharacter> {
    
    private static final long serialVersionUID = 1L;
    
    public ParamCharacter(String rawValue)
    {
         super(rawValue);
    }
    
}
