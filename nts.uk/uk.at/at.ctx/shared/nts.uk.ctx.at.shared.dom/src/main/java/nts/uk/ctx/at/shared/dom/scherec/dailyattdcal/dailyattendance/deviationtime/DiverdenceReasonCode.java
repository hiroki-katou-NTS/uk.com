package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(2)
@ZeroPaddedCode
public class DiverdenceReasonCode extends CodePrimitiveValue<DiverdenceReasonCode> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public DiverdenceReasonCode(String rawValue) {
		super(rawValue);
	}

}