package nts.uk.ctx.basic.dom.company.organization.workplace;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * 
 *職場コード
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(4)
public class WorkplaceCode extends CodePrimitiveValue<WorkplaceCode>{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public WorkplaceCode(String rawValue) {
		super(rawValue);
	}

}
