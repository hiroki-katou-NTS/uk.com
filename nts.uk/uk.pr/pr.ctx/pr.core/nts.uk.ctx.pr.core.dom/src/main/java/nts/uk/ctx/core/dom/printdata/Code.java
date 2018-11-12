package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 法定調書用会社コード
*/
@StringMaxLength(2)
@ZeroPaddedCode
@StringCharType(CharType.NUMERIC)
public class Code extends StringPrimitiveValue<Code>
{
    
    private static final long serialVersionUID = 1L;
    
    public Code(String rawValue)
    {
         super(rawValue);
    }
    
}
