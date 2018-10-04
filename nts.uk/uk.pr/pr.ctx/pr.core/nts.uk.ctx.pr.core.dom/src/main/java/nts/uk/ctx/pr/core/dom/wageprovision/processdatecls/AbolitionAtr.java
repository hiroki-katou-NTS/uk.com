package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum AbolitionAtr {
	// 0:廃止しない(not Abolition)
	NOT_ABOLITION(1),

	// 廃止(Abolition)
	ABOLITION(0);

	public final int value;
}
