package nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 都道府県名称
 */
@StringMaxLength(7)
public class PrefectureName extends StringPrimitiveValue<PrefectureName> {
    public PrefectureName(String rawValue) {
        super(rawValue);
    }
}
