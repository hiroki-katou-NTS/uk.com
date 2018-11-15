package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
* 資格グループコード
*/
@StringMaxLength(2)
@StringCharType(CharType.ALPHA_NUMERIC)
@ZeroPaddedCode
public class QualificationGroupCode extends StringPrimitiveValue<QualificationGroupCode>
{
    
    private static final long serialVersionUID = 1L;
    
    public QualificationGroupCode(String rawValue)
    {
         super(rawValue);
    }
    
}
