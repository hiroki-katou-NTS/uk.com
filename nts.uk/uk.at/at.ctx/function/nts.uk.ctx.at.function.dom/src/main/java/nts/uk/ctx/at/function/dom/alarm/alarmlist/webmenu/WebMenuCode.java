package nts.uk.ctx.at.function.dom.alarm.alarmlist.webmenu;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(3)
@StringCharType(CharType.ALPHA_NUMERIC)
public class WebMenuCode extends StringPrimitiveValue<WebMenuCode> {
    private static final long serialVersionUID = 1L;

    public WebMenuCode(String rawValue) {
        super(rawValue);

    }
}
