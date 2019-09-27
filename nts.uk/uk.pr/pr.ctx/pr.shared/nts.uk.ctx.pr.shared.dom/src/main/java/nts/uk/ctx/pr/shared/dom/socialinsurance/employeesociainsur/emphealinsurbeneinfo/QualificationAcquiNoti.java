package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 資格取得届備考内容
*/
@StringMaxLength(12)
public class QualificationAcquiNoti extends StringPrimitiveValue<QualificationAcquiNoti>
{
    
    private static final long serialVersionUID = 1L;
    
    public QualificationAcquiNoti(String rawValue)
    {
         super(rawValue);
    }
    
}
