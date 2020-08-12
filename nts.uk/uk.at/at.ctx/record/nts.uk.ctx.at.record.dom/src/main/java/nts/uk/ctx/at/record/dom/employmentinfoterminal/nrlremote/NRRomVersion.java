package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
@StringMaxLength(3)
public class NRRomVersion extends CodePrimitiveValue<NRRomVersion> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NRRomVersion(String rawValue) {
		super(rawValue);
	}

}
