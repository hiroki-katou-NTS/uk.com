package nts.uk.ctx.exio.dom.exi.condset.type.numeric.integer;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 受入条件整数
 */
@IntegerMaxValue(999999999)
@IntegerMinValue(-999999999)
public class ImportingConditionInteger extends IntegerPrimitiveValue<ImportingConditionInteger>{

	public ImportingConditionInteger(int rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;
}
