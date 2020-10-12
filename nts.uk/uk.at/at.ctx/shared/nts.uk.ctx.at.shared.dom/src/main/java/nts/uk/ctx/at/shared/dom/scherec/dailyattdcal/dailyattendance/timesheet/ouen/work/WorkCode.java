package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
@StringCharType(CharType.ALPHA_NUMERIC)
public class WorkCode extends StringPrimitiveValue<WorkCode> {

	/***/
	private static final long serialVersionUID = 1L;

	public WorkCode(String rawValue) {
		super(rawValue);
	}

}
