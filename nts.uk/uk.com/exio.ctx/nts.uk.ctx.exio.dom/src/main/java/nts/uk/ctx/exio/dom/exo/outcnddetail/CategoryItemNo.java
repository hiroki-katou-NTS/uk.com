package nts.uk.ctx.exio.dom.exo.outcnddetail;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

@IntegerMaxValue(9999)
@IntegerMinValue(1)
public class CategoryItemNo extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CategoryItemNo(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
