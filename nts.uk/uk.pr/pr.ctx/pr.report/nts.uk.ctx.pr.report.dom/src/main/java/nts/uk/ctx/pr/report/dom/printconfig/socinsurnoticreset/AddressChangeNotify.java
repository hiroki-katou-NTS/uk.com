package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
* 住所変更届その他理由
*/

@StringMaxLength(8)
public class AddressChangeNotify extends StringPrimitiveValue<AddressChangeNotify> {
    
    private static final long serialVersionUID = 1L;
    
    public AddressChangeNotify(String rawValue)
    {
         super(rawValue);
    }
    
}
