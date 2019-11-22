package nts.uk.ctx.hr.develop.dom.interview;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(17)
public class CompanyId extends StringPrimitiveValue<CompanyId> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new company id.
	 *
	 * @param rawValue the raw value
	 */
	public CompanyId(String rawValue) {
		super(rawValue);
	}
}
