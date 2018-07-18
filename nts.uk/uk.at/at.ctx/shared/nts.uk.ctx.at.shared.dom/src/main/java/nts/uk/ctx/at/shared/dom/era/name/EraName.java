package nts.uk.ctx.at.shared.dom.era.name;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class EraName.
 */
//å≥çÜ
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
