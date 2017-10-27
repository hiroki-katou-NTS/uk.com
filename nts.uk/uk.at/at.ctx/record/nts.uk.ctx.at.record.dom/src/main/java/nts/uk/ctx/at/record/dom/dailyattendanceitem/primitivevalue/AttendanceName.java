package nts.uk.ctx.at.record.dom.dailyattendanceitem.primitivevalue;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;

public class AttendanceName extends StringPrimitiveValue<PrimitiveValue<String>> {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public AttendanceName(String rawValue) {
		super(rawValue);
	}

}
