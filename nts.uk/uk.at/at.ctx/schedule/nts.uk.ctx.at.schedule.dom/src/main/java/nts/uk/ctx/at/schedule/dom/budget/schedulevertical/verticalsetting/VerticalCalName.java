package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * TanLV
 *
 */
@StringMaxLength(20)
public class VerticalCalName extends StringPrimitiveValue<VerticalCalName> {
	private static final long serialVersionUID = 1L;

	public VerticalCalName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
