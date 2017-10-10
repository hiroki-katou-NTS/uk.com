package nts.uk.ctx.bs.employee.dom.workplace.assigned;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(36)
public class AssignedWorkplaceId extends StringPrimitiveValue<AssignedWorkplaceId>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AssignedWorkplaceId(String assignedWorkplaceId){
		super(assignedWorkplaceId);
	}
}
