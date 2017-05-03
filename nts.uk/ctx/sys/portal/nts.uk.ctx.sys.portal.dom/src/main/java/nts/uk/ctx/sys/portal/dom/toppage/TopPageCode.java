package nts.uk.ctx.sys.portal.dom.toppage;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(4)
public class TopPageCode extends StringPrimitiveValue<TopPageCode> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new top page code.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public TopPageCode(String rawValue) {
		super(rawValue);
	}
}
