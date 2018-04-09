package nts.uk.ctx.at.shared.dom.common;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;


@StringMaxLength(36)
public class EmployeeId extends StringPrimitiveValue<EmployeeId>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeId(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
