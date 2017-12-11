package nts.uk.ctx.pereg.dom.copysetting.item;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum IsRequired {

	// 0:必須なし(None Required )
	NONE_REQUIRED(0),

	// 1:必須(Required)
	REQUIRED(1);

	public final int value;

}
