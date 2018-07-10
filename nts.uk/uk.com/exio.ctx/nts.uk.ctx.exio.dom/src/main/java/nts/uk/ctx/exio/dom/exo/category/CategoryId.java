package nts.uk.ctx.exio.dom.exo.category;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

@IntegerMaxValue(999)
@IntegerMinValue(1)
public class CategoryId extends IntegerPrimitiveValue<CategoryId> {

	/**
	 * int(3)
	 */
	private static final long serialVersionUID = 1L;
	
	public CategoryId(Integer rawValue) {
		super(rawValue);
		
	}

	


}
