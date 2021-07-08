package nts.uk.ctx.exio.dom.input.validation.user.type.numeric.integer;

import nts.arc.primitive.LongPrimitiveValue;
import nts.arc.primitive.constraint.LongMaxValue;
import nts.arc.primitive.constraint.LongMinValue;

/**
 * 受入条件整数
 */
@LongMaxValue(999999999)
@LongMinValue(-999999999)
public class ImportingConditionInteger extends LongPrimitiveValue<ImportingConditionInteger>{

	public ImportingConditionInteger(Long rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;
}
