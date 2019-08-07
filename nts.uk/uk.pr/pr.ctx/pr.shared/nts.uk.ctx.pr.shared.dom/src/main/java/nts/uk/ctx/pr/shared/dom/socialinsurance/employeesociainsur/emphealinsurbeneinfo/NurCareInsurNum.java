package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 介護保険番号
*/
@StringMaxLength(10)
@StringCharType(CharType.NUMERIC)
public class NurCareInsurNum extends StringPrimitiveValue<NurCareInsurNum>
{
    
    private static final long serialVersionUID = 1L;
    
    public NurCareInsurNum(String rawValue)
    {
         super(rawValue);
    }
    
}
