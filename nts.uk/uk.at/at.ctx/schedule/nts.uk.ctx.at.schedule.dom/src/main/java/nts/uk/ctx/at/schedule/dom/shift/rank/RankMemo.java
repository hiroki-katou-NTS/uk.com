package nts.uk.ctx.at.schedule.dom.shift.rank;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * ランクメモ
 * 
 * @author sonnh1
 *
 */
@StringMaxLength(200)
public class RankMemo extends StringPrimitiveValue<RankMemo> {

	private static final long serialVersionUID = 70916577866927118L;

	public RankMemo(String rawValue) {
		super(rawValue);
	}
}
