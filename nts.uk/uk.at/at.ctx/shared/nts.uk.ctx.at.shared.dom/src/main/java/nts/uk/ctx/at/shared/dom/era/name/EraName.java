package nts.uk.ctx.at.shared.dom.era.name;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(36)
public class EraName extends StringPrimitiveValue<EraName> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public EraName(String rawValue) {
		super(rawValue);
	}
}
