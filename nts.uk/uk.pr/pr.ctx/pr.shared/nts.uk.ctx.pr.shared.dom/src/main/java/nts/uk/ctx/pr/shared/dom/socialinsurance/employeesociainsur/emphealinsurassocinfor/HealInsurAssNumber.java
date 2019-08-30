package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
* 健保組合番号
*/
@StringMaxLength(7)
public class HealInsurAssNumber extends StringPrimitiveValue<HealInsurAssNumber>
{
    
    private static final long serialVersionUID = 1L;
    
    public HealInsurAssNumber(String rawValue)
    {
         super(rawValue);
    }
    
}
