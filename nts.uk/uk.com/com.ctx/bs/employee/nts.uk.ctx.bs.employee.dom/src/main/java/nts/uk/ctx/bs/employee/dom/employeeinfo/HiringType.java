package nts.uk.ctx.bs.employee.dom.employeeinfo;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(2)
public class HiringType extends IntegerPrimitiveValue<HiringType> {

	public HiringType(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
