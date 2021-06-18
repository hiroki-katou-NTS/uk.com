package nts.uk.ctx.at.shared.dom.scherec.application.optional;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 任意項目申請種類コード
 */

@StringCharType(CharType.NUMERIC)
@StringMaxLength(2)
@ZeroPaddedCode
public class OptionalItemApplicationTypeCodeShare extends CodePrimitiveValue<OptionalItemApplicationTypeCodeShare> {
	private static final long serialVersionUID = 1L;

	public OptionalItemApplicationTypeCodeShare(String rawValue) {
        super(rawValue);
    }
}
