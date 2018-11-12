package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 金融機関名称
*/
@StringMaxLength(40)
public class NameBankTranfeIns extends StringPrimitiveValue<NameBankTranfeIns>
{
    
    private static final long serialVersionUID = 1L;
    
    public NameBankTranfeIns(String rawValue)
    {
         super(rawValue);
    }
    
}
