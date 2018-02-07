package nts.uk.ctx.at.request.dom.setting.request.application;
/**
 * 月間日数
 * @author dudt
 *
 */

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(max = 31, min = 1)
public class Deadline extends IntegerPrimitiveValue<Deadline> {

	public Deadline(int rawValue) {
		super(rawValue);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
}
