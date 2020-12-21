package nts.uk.ctx.at.function.dom.scheduledailytable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class ScheduleDailyTableName extends StringPrimitiveValue<ScheduleDailyTableName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 634576031617622920L;

	public ScheduleDailyTableName(String rawValue) {
		super(rawValue);
	}

}
