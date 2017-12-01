package nts.uk.ctx.bs.employee.dom.workplace.info;

import nts.arc.primitive.constraint.StringRegEx;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * 
 *職場コード
 */
@StringRegEx("^[a-zA-Z0-9_-]{1,10}$")
public class WorkplaceCode extends CodePrimitiveValue<WorkplaceCode>{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public WorkplaceCode(String rawValue) {
		super(rawValue);
	}

}
