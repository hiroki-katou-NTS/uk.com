package nts.uk.ctx.pr.proto.dom.paymentdata;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * メモ
 */
@StringMaxLength(2)
public class comment extends StringPrimitiveValue<comment> {

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
