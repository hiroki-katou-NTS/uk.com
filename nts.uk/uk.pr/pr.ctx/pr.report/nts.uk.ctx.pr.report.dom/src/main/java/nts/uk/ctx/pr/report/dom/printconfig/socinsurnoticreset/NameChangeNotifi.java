package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
* 氏名変更届備考
*/
@StringMaxLength(10)
public class NameChangeNotifi extends StringPrimitiveValue<NameChangeNotifi>
{
    
    private static final long serialVersionUID = 1L;
    
    public NameChangeNotifi(String rawValue)
    {
         super(rawValue);
    }
    
}
