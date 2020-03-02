package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 資格コード
 */
@StringMaxLength(10)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class QualificationCode extends StringPrimitiveValue<QualificationCode> {

	private static final long serialVersionUID = 1L;

	public QualificationCode(String rawValue) {
		super(rawValue);
	}

}