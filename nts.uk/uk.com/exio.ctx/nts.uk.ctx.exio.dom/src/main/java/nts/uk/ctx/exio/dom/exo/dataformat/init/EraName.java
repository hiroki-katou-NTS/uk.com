package nts.uk.ctx.exio.dom.exo.dataformat.init;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * EraName.
 */

@StringMaxLength(4)
public class EraName extends StringPrimitiveValue<EraName> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new era name.
	 *
	 * @param rawValue the raw value
	 */
	public EraName(String rawValue) {
		super(rawValue);
	}
}
