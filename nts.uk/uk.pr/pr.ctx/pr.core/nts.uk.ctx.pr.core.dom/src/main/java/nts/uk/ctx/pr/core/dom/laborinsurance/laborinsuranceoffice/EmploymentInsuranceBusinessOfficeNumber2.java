package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
* 事業所記号
*/
@StringMaxLength(6)
@StringCharType(CharType.ALPHA_NUMERIC)
public class EmploymentInsuranceBusinessOfficeNumber2 extends StringPrimitiveValue<EmploymentInsuranceBusinessOfficeNumber2>
{

    private static final long serialVersionUID = 1L;

    public EmploymentInsuranceBusinessOfficeNumber2(String rawValue)
    {
         super(rawValue);
    }
    
}
