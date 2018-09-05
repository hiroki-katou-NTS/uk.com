package nts.uk.ctx.sys.assist.dom.salary;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum AbolitionAtr {
	// 0:廃止しない(not Abolition)
	NOT_ABOLITION(0),

	// 廃止(Abolition)
	ABOLITION(1);

	public final int value;
}
