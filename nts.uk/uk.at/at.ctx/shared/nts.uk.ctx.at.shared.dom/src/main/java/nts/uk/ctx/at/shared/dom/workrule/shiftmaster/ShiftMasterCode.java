package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(3)
@ZeroPaddedCode
public class ShiftMasterCode extends CodePrimitiveValue<ShiftMasterCode> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public ShiftMasterCode(String rawValue) {
		super(rawValue);
	}

}
