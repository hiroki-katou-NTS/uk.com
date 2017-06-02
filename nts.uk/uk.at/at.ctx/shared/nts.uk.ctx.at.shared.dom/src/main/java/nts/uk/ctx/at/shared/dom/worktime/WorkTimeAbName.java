package nts.uk.ctx.at.shared.dom.worktime;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@StringMaxLength(6)
public class WorkTimeAbName extends StringPrimitiveValue<WorkTimeAbName>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1390924144844546717L;

	public WorkTimeAbName(String rawValue) {
		super(rawValue);
	}
	
	

}
