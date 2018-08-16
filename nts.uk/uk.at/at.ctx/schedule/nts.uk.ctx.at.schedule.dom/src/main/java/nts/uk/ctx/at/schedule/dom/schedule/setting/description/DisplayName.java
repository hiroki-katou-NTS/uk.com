package nts.uk.ctx.at.schedule.dom.schedule.setting.description;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(60)
public class DisplayName extends StringPrimitiveValue<DisplayName>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs.
	 * 
	 * @param rawValue
	 */
	public DisplayName(String rawValue) {
		super(rawValue);
	}
}
