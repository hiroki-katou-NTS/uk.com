package nts.uk.ctx.sys.assist.dom.storage;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class EmployeeCode.
 */
// 社員CD
@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLength(12)
public class EmployeeCode extends StringPrimitiveValue<EmployeeCode> {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new employee code.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public EmployeeCode(String rawValue) {
		super(rawValue);
	}
}
