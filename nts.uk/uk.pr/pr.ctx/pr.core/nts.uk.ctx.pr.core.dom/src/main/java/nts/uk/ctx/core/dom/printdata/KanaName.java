package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.KanaPrimitiveValue;

/**
* 法定調書用会社カナ名
*/
@StringMaxLength(40)
public class KanaName extends KanaPrimitiveValue<KanaName>
{
    
    private static final long serialVersionUID = 1L;
    
    public KanaName(String rawValue)
    {
         super(rawValue);
    }
    
}
