package nts.uk.ctx.pereg.dom.person.info.category;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum AddItemObjCls {
	DISABLE(0),
	ENABLE(1);

	public final int value;
}
