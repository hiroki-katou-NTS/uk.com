package nts.uk.ctx.exio.dom.exo.outitem;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/*
 * カテゴリID
 */
@IntegerMaxValue(999)
@IntegerMinValue(1)
public class CtgId extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {

	private static final long serialVersionUID = 1L;

	/**
	 * Contructor
	 * 
	 * @param rawValue
	 */
	public CtgId(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
