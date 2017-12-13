package nts.uk.ctx.pereg.dom.person.info.category;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum IsAbolition {
	
	// 0:廃止しない(not Abolition)
	NOT_ABOLITION(0),
	
	// 廃止(Abolition)
	ABOLITION(1);

	public final int value;

}
