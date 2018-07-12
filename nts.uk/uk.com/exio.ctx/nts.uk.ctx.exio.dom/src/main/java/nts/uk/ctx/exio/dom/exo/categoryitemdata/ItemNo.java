package nts.uk.ctx.exio.dom.exo.categoryitemdata;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/*
 * カテゴリ項目NO
 */
@IntegerMinValue(1)
@IntegerMaxValue(9999)
public class ItemNo extends IntegerPrimitiveValue<ItemNo> {
	private static final long serialVersionUID = 1L;

	public ItemNo(int rawValue) {
		super(rawValue);
	}
}
