package nts.uk.ctx.at.schedule.dom.shift.team;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author sonnh1
 *
 */
@StringMaxLength(2)
public class TeamCode extends StringPrimitiveValue<TeamCode> {

	public TeamCode(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;
}
