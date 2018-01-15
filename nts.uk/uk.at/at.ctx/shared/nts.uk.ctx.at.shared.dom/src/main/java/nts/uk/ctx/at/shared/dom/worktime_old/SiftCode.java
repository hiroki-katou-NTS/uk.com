package nts.uk.ctx.at.shared.dom.worktime_old;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@StringMaxLength(3)
public class SiftCode extends StringPrimitiveValue<SiftCode>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4042037564670742671L;

	public SiftCode(String rawValue) {
		super(rawValue);
	}

}
