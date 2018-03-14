package nts.uk.ctx.at.record.dom.divergence.time.setting;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(2)
public class DivergenceReasonCode extends CodePrimitiveValue<DivergenceReasonCode> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public DivergenceReasonCode (String rawValue) {
		super(rawValue);
	}

	
}
