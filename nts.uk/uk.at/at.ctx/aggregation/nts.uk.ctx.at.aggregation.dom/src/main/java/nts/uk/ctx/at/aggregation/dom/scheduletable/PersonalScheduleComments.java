package nts.uk.ctx.at.aggregation.dom.scheduletable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author quytb
 *
 */
@StringMaxLength(400)
public class PersonalScheduleComments extends StringPrimitiveValue<PersonalScheduleComments> {
	private static final long serialVersionUID = 1229049281056214812L;

	public PersonalScheduleComments(String rawValue) {
		super(rawValue);
	}
}
