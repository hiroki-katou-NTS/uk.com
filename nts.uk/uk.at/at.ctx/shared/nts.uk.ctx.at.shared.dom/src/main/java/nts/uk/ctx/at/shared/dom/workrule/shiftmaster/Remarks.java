/**
 * 
 */
package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author tutk
 * シフトマスタ備考
 */
@StringMaxLength(40)
public class Remarks extends StringPrimitiveValue<Remarks>{
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param rawValue
	 */
	public Remarks(String rawValue) {
		super(rawValue);
	}

	

}
