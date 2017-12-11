package nts.uk.ctx.bs.employee.dom.workplace.info;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class WorkplaceCode.
 */
// 職場コード
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(10)
public class WorkplaceCode extends StringPrimitiveValue<WorkplaceCode>{

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new workplace code.
	 *
	 * @param rawValue the raw value
	 */
	public WorkplaceCode(String rawValue) {
		super(rawValue);
	}

}
