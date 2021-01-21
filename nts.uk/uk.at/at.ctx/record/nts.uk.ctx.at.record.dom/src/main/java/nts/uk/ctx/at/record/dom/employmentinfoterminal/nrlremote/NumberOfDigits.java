package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * @author ThanhNX
 *
 *         入力桁数
 */
@IntegerRange(min = 0, max = 999)
public class NumberOfDigits extends IntegerPrimitiveValue<NumberOfDigits> {

	private static final long serialVersionUID = 1L;

	public NumberOfDigits(Integer rawValue) {
		super(rawValue);
	}

}
