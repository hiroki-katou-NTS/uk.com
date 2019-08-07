package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 健康保険番号
*/
@StringMaxLength(8)
@StringCharType(CharType.NUMERIC)
public class HealInsurNumber extends StringPrimitiveValue<HealInsurNumber>
{
    
    private static final long serialVersionUID = 1L;
    
    public HealInsurNumber(String rawValue)
    {
         super(rawValue);
    }
    
}
