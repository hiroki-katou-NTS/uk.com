package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * TanLV
 *
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(2)
@ZeroPaddedCode
public class VerticalCalCd extends StringPrimitiveValue<VerticalCalCd> {
	private static final long serialVersionUID = 1L;

	public VerticalCalCd(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
