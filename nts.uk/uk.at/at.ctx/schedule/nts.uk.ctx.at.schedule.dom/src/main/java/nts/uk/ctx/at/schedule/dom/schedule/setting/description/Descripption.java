package nts.uk.ctx.at.schedule.dom.schedule.setting.description;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(200)
public class Descripption extends StringPrimitiveValue<Descripption>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs.
	 * 
	 * @param rawValue
	 */
	public Descripption(String rawValue) {
		super(rawValue);
	}
}