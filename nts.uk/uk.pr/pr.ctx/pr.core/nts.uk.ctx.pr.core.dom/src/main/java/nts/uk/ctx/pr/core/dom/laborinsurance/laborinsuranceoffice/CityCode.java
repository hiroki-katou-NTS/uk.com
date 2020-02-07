package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 都市区符号
*/
@StringMaxLength(2)
@StringCharType(CharType.ALPHA_NUMERIC)
public class CityCode extends StringPrimitiveValue<CityCode>
{
    
    private static final long serialVersionUID = 1L;
    
    public CityCode(String rawValue)
    {
         super(rawValue);
    }
    
}
