package nts.uk.ctx.at.shared.dom.calculation.holiday.time;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author phongtq
 *
 */
@StringMaxLength(12)
public class OvertimeFrameName extends StringPrimitiveValue<PrimitiveValue<String>> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public OvertimeFrameName(String rawValue) {
		super(rawValue);
	}
}