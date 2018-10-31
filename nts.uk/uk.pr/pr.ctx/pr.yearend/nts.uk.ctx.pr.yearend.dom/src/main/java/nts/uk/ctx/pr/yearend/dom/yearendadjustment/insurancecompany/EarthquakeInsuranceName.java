package nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 地震保険名称
*/
@StringMaxLength(40)
public class EarthquakeInsuranceName extends StringPrimitiveValue<EarthquakeInsuranceName>
{
    
    private static final long serialVersionUID = 1L;
    
    public EarthquakeInsuranceName(String rawValue)
    {
         super(rawValue);
    }
    
}
