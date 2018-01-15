package nts.uk.ctx.at.schedule.dom.shift.team;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author sonnh1
 *
 */
@StringMaxLength(12)
public class TeamName extends StringPrimitiveValue<TeamName> {

	public TeamName(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;
}
