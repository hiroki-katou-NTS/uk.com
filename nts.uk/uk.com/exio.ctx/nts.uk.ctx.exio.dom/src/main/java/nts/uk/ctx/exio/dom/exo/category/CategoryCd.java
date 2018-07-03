package nts.uk.ctx.exio.dom.exo.category;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 外部出力カテゴリコード
 */
@IntegerMaxValue(999)
@IntegerMinValue(1)
public class CategoryCd extends IntegerPrimitiveValue<CategoryCd> {

	private static final long serialVersionUID = 1L;

	public CategoryCd(int rawValue) {
		super(rawValue);
	}

}