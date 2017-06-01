package nts.uk.file.pr.app.export.comparingsalarybonus.data;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(20)
public class DepartmentName extends StringPrimitiveValue<DepartmentName>{

	public DepartmentName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
