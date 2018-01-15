package nts.uk.ctx.bs.employee.dom.position.jobtitlemaster;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class PositionName extends StringPrimitiveValue<PositionName>{

	public PositionName(String rawValue) {
		super(rawValue);
	}

}
