package nts.uk.ctx.pr.proto.dom.layoutmaster;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;

@IntegerMaxValue(9)
public class CategoryPosition extends IntegerPrimitiveValue<CategoryPosition>{

	public CategoryPosition(Integer rawValue) {
		super(rawValue);
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

}
