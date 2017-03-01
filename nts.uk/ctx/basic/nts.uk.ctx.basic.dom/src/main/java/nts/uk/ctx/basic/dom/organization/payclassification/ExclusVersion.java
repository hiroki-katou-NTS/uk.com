package nts.uk.ctx.basic.dom.organization.payclassification;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;

@IntegerMaxValue(8)
public class ExclusVersion extends IntegerPrimitiveValue<ExclusVersion> {

	public ExclusVersion(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
