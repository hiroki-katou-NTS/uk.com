package nts.uk.ctx.at.shared.dom.yearholidaygrant;


import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 1, max = 20)
public class GrantNum extends IntegerPrimitiveValue<GrantNum>{

	public GrantNum(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
