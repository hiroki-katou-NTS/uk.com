package nts.uk.ctx.bs.employee.dom.employeeinfo.contact;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(20)
public class SeatNo extends StringPrimitiveValue<SeatNo>{

	private static final long serialVersionUID = 1L;

	public SeatNo(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
