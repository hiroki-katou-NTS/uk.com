package nts.uk.ctx.at.schedule.dom.shift.team;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 
 * @author sonnh1
 *
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(2)
@ZeroPaddedCode
public class TeamCode extends StringPrimitiveValue<TeamCode> {

	public TeamCode(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;
}
