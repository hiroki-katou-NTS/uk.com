package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 労働保険事業所代表者職位
*/
@StringMaxLength(10)
public class RepresentativePosition extends StringPrimitiveValue<RepresentativePosition>
{
    
    private static final long serialVersionUID = 1L;
    
    public RepresentativePosition(String rawValue)
    {
         super(rawValue);
    }
    
}
