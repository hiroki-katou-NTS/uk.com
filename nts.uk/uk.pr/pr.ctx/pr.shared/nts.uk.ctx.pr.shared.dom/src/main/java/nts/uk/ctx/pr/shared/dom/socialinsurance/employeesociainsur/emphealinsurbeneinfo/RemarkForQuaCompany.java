package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 社保資格得喪用備考
*/
@StringMaxLength(12)
public class RemarkForQuaCompany extends StringPrimitiveValue<RemarkForQuaCompany>
{
    
    private static final long serialVersionUID = 1L;
    
    public RemarkForQuaCompany(String rawValue)
    {
         super(rawValue);
    }
    
}
