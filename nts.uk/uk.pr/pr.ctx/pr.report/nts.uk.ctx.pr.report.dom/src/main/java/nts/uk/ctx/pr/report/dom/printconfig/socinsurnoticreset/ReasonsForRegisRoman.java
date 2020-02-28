package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* ローマ字氏名届理由内容
*/
@StringMaxLength(30)
public class ReasonsForRegisRoman extends StringPrimitiveValue<ReasonsForRegisRoman> {
    
    private static final long serialVersionUID = 1L;
    
    public ReasonsForRegisRoman(String rawValue)
    {
         super(rawValue);
    }
    
}
