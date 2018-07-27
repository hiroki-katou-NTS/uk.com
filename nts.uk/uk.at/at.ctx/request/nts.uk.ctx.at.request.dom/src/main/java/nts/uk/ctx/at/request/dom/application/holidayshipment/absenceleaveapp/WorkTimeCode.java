package nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

//就業時間帯コード
@StringMaxLength(3)
@StringCharType(CharType.ALPHA_NUMERIC)
public class WorkTimeCode extends StringPrimitiveValue<WorkTimeCode> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new employment timezone code.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public WorkTimeCode(String rawValue) {
		super(rawValue);
	}

}
