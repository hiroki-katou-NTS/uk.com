package nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 生命保険名称
*/
@StringMaxLength(40)
public class LifeInsuranceName extends StringPrimitiveValue<LifeInsuranceName>
{
    
    private static final long serialVersionUID = 1L;
    
    public LifeInsuranceName(String rawValue)
    {
         super(rawValue);
    }
    
}
