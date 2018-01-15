package nts.uk.ctx.pereg.dom.person.info.stringitem;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 2, min = 1)
public enum StringItemDataType {

	// 1:固定長(FixedLength)
	FIXED_LENGTH(1),

	// 2:可変長(VariableLength)
	VARIABLE_LENGTH(2);

	public final int value;
}
