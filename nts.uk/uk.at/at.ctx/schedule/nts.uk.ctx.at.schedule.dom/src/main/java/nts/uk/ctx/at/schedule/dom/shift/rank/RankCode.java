package nts.uk.ctx.at.schedule.dom.shift.rank;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * ランクコード
 * @author sonnh1
 *
 */
@StringMaxLength(4)
public class RankCode extends StringPrimitiveValue<RankCode> {
	
	private static final long serialVersionUID = 1L;
	
	public RankCode(String rawValue) {
		super(rawValue);
	}
}
