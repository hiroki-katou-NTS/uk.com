package nts.uk.ctx.pr.report.dom.printdata.comlegalrecord;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

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
