package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
* 資格グループコード
*/
@StringMaxLength(2)
@StringCharType(CharType.ALPHA_NUMERIC)
public class QualificationGroupCode extends StringPrimitiveValue<QualificationGroupCode>
{
    
    private static final long serialVersionUID = 1L;
    
    public QualificationGroupCode(String rawValue)
    {
         super(rawValue);
    }
    
}
