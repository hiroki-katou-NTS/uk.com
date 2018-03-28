package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author sang.nv
 * 社員コード
 *
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(12)
public class EmployeeCode extends StringPrimitiveValue<PrimitiveValue<String>>  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param rawValue
	 */
	public EmployeeCode(String rawValue) {
		super(rawValue);
	}
}
