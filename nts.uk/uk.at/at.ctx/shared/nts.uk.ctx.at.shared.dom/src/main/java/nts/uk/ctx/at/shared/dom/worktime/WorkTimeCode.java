package nts.uk.ctx.at.shared.dom.worktime;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@StringMaxLength(3)
public class WorkTimeCode extends StringPrimitiveValue<WorkTimeCode>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4042037564670742671L;

	public WorkTimeCode(String rawValue) {
		super(rawValue);
	}

}
