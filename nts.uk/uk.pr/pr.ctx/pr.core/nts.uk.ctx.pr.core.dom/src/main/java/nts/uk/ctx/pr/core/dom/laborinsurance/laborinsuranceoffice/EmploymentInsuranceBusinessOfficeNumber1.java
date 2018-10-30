package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
* 雇用保険事業所番号1
*/
@StringMaxLength(4)
@StringCharType(CharType.ALPHA_NUMERIC)
public class EmploymentInsuranceBusinessOfficeNumber1 extends StringPrimitiveValue<EmploymentInsuranceBusinessOfficeNumber1>
{

    private static final long serialVersionUID = 1L;

    public EmploymentInsuranceBusinessOfficeNumber1(String rawValue)
    {
         super(rawValue);
    }
    
}
