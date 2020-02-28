package nts.uk.ctx.pr.core.dom.wageprovision.unitpricename;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 個人単価名称略名
*/
@StringMaxLength(12)
public class PerUnitPriceShortName extends StringPrimitiveValue<PerUnitPriceShortName>
{
    
    private static final long serialVersionUID = 1L;
    
    public PerUnitPriceShortName(String rawValue)
    {
         super(rawValue);
    }
    
}
