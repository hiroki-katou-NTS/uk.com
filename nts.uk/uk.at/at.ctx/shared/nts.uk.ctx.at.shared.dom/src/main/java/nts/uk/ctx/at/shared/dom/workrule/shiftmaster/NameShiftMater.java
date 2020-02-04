/**
 * 
 */
package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author tutk
 *
 */
@StringMaxLength(4)
public class NameShiftMater extends StringPrimitiveValue<NameShiftMater>{
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param rawValue
	 */
	public NameShiftMater(String rawValue) {
		super(rawValue);
	}

	

}
