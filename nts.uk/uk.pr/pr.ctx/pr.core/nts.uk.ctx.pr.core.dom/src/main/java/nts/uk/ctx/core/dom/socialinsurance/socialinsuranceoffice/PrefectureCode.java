package nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 都道府県コード
 */
@StringMaxLength(2)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class PrefectureCode extends CodePrimitiveValue<PrefectureCode> {
    public PrefectureCode(String rawValue) {
        super(rawValue);
    }
}
