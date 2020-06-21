package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(50)
public class StampTypeDisplay extends StringPrimitiveValue<StampTypeDisplay> {

	private static final long serialVersionUID = 1L;

	public StampTypeDisplay(String rawValue) {
		super(rawValue);
	}

}
