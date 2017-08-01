package nts.uk.ctx.bs.person.dom.person.info.category;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum IsUsed {
	// 0:しない(no)
	NO(0),
	// 1:する(yes)
	YES(1);

	public final int value;

}
