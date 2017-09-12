package nts.uk.ctx.bs.employee.dom.department_old;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * 部門名称
 */
@StringMaxLength(20)
public class DepartmentName extends StringPrimitiveValue<DepartmentName> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public DepartmentName(String rawValue) {
		super(rawValue);
	}

}
