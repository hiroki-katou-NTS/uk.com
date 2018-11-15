package nts.uk.ctx.pr.core.dom.wageprovision.orginfo.salarycls.salaryclsmaster;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(500)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class Memo extends StringPrimitiveValue<Memo> {

    public Memo(String rawValue) {
        super(rawValue);
    }
}
