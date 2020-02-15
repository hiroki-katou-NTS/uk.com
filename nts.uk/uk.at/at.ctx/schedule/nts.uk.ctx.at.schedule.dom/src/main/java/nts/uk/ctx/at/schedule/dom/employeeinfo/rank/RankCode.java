package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * ランクコード
 * 
 * @author sonnh1
 *
 */
@StringMaxLength(3)
@StringCharType(CharType.ALPHA_NUMERIC)
@ZeroPaddedCode
public class RankCode extends StringPrimitiveValue<RankCode> {
	
	private static final long serialVersionUID = 1L;
	
	public RankCode(String rawValue) {
		super(rawValue);
	}
}
