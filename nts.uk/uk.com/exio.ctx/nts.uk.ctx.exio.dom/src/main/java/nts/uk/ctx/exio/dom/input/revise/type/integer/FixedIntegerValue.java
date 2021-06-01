package nts.uk.ctx.exio.dom.input.revise.type.integer;

import nts.arc.primitive.LongPrimitiveValue;
import nts.arc.primitive.constraint.LongMaxValue;
import nts.arc.primitive.constraint.LongMinValue;

/**
 * 整数型固定値
 */
@LongMaxValue(999999999)
@LongMinValue(-999999999)
public class FixedIntegerValue extends LongPrimitiveValue<FixedIntegerValue>{

	public FixedIntegerValue(Long rawValue) {
		super(rawValue);
	}
}
