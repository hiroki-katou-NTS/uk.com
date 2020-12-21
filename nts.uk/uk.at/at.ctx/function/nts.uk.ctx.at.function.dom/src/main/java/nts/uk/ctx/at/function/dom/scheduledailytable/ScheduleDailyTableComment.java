package nts.uk.ctx.at.function.dom.scheduledailytable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(200)
public class ScheduleDailyTableComment extends StringPrimitiveValue<ScheduleDailyTableComment> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3455730316604059843L;

	public ScheduleDailyTableComment(String rawValue) {
		super(rawValue);
	}

}
