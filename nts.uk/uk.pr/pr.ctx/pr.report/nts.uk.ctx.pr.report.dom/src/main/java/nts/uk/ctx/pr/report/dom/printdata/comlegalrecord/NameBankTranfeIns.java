package nts.uk.ctx.pr.report.dom.printdata.comlegalrecord;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

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
