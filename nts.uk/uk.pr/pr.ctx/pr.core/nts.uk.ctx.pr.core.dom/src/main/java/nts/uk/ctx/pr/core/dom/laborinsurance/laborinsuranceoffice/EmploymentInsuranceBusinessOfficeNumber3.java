package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
* 雇用保険事業所番号3
*/
@StringMaxLength(1)
@StringCharType(CharType.ALPHA_NUMERIC)
public class EmploymentInsuranceBusinessOfficeNumber3 extends StringPrimitiveValue<EmploymentInsuranceBusinessOfficeNumber3>
{

    private static final long serialVersionUID = 1L;

    public EmploymentInsuranceBusinessOfficeNumber3(String rawValue)
    {
         super(rawValue);
    }
    
}
