package nts.uk.ctx.exio.dom.exo.outputitem;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/*
 * カテゴリ項目NO
 */
@IntegerMaxValue(9999)
@IntegerMinValue(1)
public class CategoryItemNo extends IntegerPrimitiveValue<CategoryItemNo> {
	private static final long serialVersionUID = 1L;

	public CategoryItemNo(Integer rawValue) {
		super(rawValue);
	}
}
