package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * TanLV
 *
 */
@StringMaxLength(30)
public class ItemName extends StringPrimitiveValue<ItemName> {
	private static final long serialVersionUID = 1L;

	public ItemName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}