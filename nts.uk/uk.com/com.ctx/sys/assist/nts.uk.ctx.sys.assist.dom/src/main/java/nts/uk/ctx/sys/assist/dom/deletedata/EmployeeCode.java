package nts.uk.ctx.sys.assist.dom.deletedata;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(12)
@StringCharType(CharType.ALPHA_NUMERIC)
public class EmployeeCode extends StringPrimitiveValue<EmployeeCode> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new business name.
	 *
	 * @param rawValue the raw value
	 */
	public EmployeeCode(String rawValue) {
		super(rawValue);
	}
}
