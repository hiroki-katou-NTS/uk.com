package nts.uk.ctx.at.shared.dom.remainingnumber.base;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
// thay đổi theo EA 修正履歴　履歴＃1475 từ alphanumberic -> numberic
@StringMaxLength(2)
@StringCharType(CharType.NUMERIC)
public class PerServiceLengthTableCD extends CodePrimitiveValue<PerServiceLengthTableCD>{

	private static final long serialVersionUID = 1L;

	public PerServiceLengthTableCD(String rawValue) {
		super(rawValue);
	}

}
