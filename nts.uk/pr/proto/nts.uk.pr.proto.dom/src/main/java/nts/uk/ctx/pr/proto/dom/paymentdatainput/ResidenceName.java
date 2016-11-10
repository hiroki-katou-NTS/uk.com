package nts.uk.ctx.pr.proto.dom.paymentdatainput;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLengh;

/**
 * 住民税納付先名
 */
@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLengh(24)
public class ResidenceName extends StringPrimitiveValue<ResidenceName> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs.
	 * 
	 * @param rawValue
	 *            raw value
	 */
	public ResidenceName(String rawValue) {
		super(rawValue);
	}

}
