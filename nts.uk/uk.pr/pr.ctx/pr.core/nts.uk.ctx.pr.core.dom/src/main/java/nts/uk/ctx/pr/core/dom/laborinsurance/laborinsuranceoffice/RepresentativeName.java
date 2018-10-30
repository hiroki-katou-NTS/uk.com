package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 労働保険事業所代表者名
*/
@StringMaxLength(10)
public class RepresentativeName extends StringPrimitiveValue<RepresentativeName>
{
    
    private static final long serialVersionUID = 1L;
    
    public RepresentativeName(String rawValue)
    {
         super(rawValue);
    }
    
}
