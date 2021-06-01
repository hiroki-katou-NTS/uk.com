package nts.uk.ctx.exio.dom.input.revise.type.string;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 文字型固定値
 */
@StringMaxLength(30)
public class FixedStringValue extends StringPrimitiveValue<FixedStringValue> {
	
	public FixedStringValue(String rawValue) {
		super(rawValue);
	}
}
