package nts.uk.ctx.at.shared.dom.worktime;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@StringMaxLength(12)
public class WorkTimeName extends StringPrimitiveValue<WorkTimeName>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2553705539399959368L;

	public WorkTimeName(String rawValue) {
		super(rawValue);
	}

}
