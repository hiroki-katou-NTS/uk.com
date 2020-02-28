package nts.uk.ctx.hr.develop.dom.interview;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(41)
public class MainInterviewerName extends StringPrimitiveValue<MainInterviewerName>{

	public MainInterviewerName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
