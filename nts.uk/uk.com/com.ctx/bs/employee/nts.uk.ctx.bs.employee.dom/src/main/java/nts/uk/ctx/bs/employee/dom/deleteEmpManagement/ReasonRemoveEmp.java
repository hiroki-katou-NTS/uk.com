package nts.uk.ctx.bs.employee.dom.deleteEmpManagement;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(1000)
public class ReasonRemoveEmp extends StringPrimitiveValue<ReasonRemoveEmp>{

	public ReasonRemoveEmp(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
