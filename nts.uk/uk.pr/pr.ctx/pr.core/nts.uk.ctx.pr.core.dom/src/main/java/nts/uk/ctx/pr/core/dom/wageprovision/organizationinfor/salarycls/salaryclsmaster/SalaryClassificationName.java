package nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclsmaster;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
* 給与分類名称
*/

@StringMaxLength(20)
public class SalaryClassificationName extends StringPrimitiveValue<SalaryClassificationName>
{
    
    private static final long serialVersionUID = 1L;
    
    public SalaryClassificationName(String rawValue)
    {
         super(rawValue);
    }
    
}
