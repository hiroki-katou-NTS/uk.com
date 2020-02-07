package nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.insuranceType;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 保険種類名称
*/
@StringMaxLength(40)
public class InsuranceTypeName extends StringPrimitiveValue<InsuranceTypeName>
{
    
    private static final long serialVersionUID = 1L;
    
    public InsuranceTypeName(String rawValue)
    {
         super(rawValue);
    }
    
}
