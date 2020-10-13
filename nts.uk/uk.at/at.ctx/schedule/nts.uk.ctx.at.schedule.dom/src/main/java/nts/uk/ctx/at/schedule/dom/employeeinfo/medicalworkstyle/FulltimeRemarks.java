package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 常勤換算表用備考
 * @author HieuLt
 *
 */

@StringMaxLength(200)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class FulltimeRemarks extends StringPrimitiveValue<FulltimeRemarks> {

	private static final long serialVersionUID = 1L;
	
	public FulltimeRemarks(String rawValue) {
		super(rawValue);
	
	}

}
