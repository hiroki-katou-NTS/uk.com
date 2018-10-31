package nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 地震保険コード
*/
@StringMaxLength(2)
@ZeroPaddedCode
@StringCharType(CharType.NUMERIC)
public class EarthquakeInsuranceCode extends StringPrimitiveValue<EarthquakeInsuranceCode>
{
    
    private static final long serialVersionUID = 1L;
    
    public EarthquakeInsuranceCode(String rawValue)
    {
         super(rawValue);
    }
    
}
