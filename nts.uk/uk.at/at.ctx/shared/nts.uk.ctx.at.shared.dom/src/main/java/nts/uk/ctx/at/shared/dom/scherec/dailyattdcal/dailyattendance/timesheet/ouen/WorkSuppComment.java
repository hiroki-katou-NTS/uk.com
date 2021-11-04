package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 作業補足コメント
 * @author tutt
 *
 */
@StringMaxLength(100)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class WorkSuppComment extends StringPrimitiveValue<WorkSuppComment> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public WorkSuppComment(String rawValue) {
		super(rawValue);
	}

}
