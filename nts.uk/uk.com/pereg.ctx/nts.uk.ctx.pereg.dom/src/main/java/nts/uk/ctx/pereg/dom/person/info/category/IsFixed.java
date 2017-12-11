package nts.uk.ctx.pereg.dom.person.info.category;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum IsFixed {
	
	// 0:固定なし(Not Fixed)
	NOT_FIXED(0),

	// 1:固定(Fixed)
	FIXED(1);

	public final int value;

}