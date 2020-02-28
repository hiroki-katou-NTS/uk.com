package nts.uk.ctx.pr.core.dom.wageprovision.unitpricename;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 個人単価名称
*/
@StringMaxLength(20)
public class PerUnitPriceName extends StringPrimitiveValue<PerUnitPriceName>
{
    
    private static final long serialVersionUID = 1L;
    
    public PerUnitPriceName(String rawValue)
    {
         super(rawValue);
    }
    
}
