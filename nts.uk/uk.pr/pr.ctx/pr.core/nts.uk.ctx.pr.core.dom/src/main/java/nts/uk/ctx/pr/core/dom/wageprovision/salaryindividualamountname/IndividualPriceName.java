package nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 個人金額名称
*/
@StringMaxLength(20)
public class IndividualPriceName extends StringPrimitiveValue<IndividualPriceName>
{
    
    private static final long serialVersionUID = 1L;
    
    public IndividualPriceName(String rawValue)
    {
         super(rawValue);
    }
    
}
