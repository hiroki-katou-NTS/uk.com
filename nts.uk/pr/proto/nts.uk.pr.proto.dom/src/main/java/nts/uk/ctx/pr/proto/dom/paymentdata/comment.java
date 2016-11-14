package nts.uk.ctx.pr.proto.dom.paymentdata;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLengh;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * 明細書コード
 */
@StringCharType(CharType.NUMERIC)
@StringMaxLengh(2)
public class comment extends CodePrimitiveValue<comment> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs.
	 * 
	 * @param rawValue
	 *            raw value
	 */
	public comment(String rawValue) {
		super(rawValue);
	}

}
