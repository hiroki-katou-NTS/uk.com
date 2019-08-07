package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 資格取得届理由内容
*/
@StringMaxLength(10)
public class ReaForNotiOfQuatification extends StringPrimitiveValue<ReaForNotiOfQuatification>
{

    private static final long serialVersionUID = 1L;
    
    public ReaForNotiOfQuatification(String rawValue)
    {
         super(rawValue);
    }
    
}
