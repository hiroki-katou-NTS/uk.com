package nts.uk.ctx.core.dom.company;

import nts.arc.primitive.StringPrimitiveValue;

/**
 * Name of Company
 */
public class CompanyName extends StringPrimitiveValue<CompanyName> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs.
	 * 
	 * @param rawValue raw value
	 */
	public CompanyName(String rawValue) {
		super(rawValue);
	}

}
