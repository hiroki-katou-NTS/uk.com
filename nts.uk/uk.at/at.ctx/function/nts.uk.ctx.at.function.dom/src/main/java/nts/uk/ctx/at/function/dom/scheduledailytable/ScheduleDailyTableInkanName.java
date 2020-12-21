package nts.uk.ctx.at.function.dom.scheduledailytable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(8)
public class ScheduleDailyTableInkanName extends  StringPrimitiveValue<ScheduleDailyTableInkanName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4677917277045690855L;
	
	public ScheduleDailyTableInkanName(String rawValue) {
		super(rawValue);
	}

}
