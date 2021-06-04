package nts.uk.ctx.at.function.dom.alarm.alarmlist.webmenu;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(2)
@ZeroPaddedCode
public class CategoryCode extends CodePrimitiveValue<CategoryCode> {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    /** カテゴリコード **/
    public CategoryCode(String rawValue){
        super(rawValue);
    }
}

