package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * ランク記号
 * 
 * @author sonnh1
 *
 */
@StringMaxLength(4)
public class RankSymbol extends StringPrimitiveValue<RankSymbol> {

	private static final long serialVersionUID = 70916577866927118L;

	public RankSymbol(String rawValue) {
		super(rawValue);
	}
}
