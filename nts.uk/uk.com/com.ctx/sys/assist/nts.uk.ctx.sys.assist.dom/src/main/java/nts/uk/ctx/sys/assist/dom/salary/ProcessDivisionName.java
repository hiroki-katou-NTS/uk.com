package nts.uk.ctx.sys.assist.dom.salary;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(24)
public class ProcessDivisionName extends StringPrimitiveValue<ProcessDivisionName>{
private static final long serialVersionUID = 1L;
	
	public ProcessDivisionName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
