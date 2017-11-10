package nts.uk.ctx.at.shared.dom.worktime_old;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@StringMaxLength(100)
public class WorkTimeNote extends StringPrimitiveValue<WorkTimeNote>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8667178942183878371L;

	public WorkTimeNote(String rawValue) {
		super(rawValue);
	}

}
