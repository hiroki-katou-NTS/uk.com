package nts.uk.ctx.pr.report.dom.printdata.comlegalrecord;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
* 会計事務所名称
*/
@StringMaxLength(40)
public class AccountingOffName extends StringPrimitiveValue<AccountingOffName>
{
    
    private static final long serialVersionUID = 1L;
    
    public AccountingOffName(String rawValue)
    {
         super(rawValue);
    }
    
}