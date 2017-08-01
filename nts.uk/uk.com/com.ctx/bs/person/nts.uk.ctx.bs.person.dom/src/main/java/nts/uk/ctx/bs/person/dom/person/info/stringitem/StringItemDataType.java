package nts.uk.ctx.bs.person.dom.person.info.stringitem;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StringItemDataType {

	// 1:固定長(FixedLength)
	FIXED_LENGTH(1),

	// 2:可変長(VariableLength)
	VARIABLE_LENGTH(2);

	private final int value;
}
