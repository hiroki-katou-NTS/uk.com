package nts.uk.ctx.at.request.dom.setting.request.application;
/**
 * 月間日数
 * @author dudt
 *
 */

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringCharType(CharType.NUMERIC)
@StringMaxLength(2)
public class Deadline extends IntegerPrimitiveValue<Deadline> {

	public Deadline(int rawValue) {
		super(rawValue);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
}
