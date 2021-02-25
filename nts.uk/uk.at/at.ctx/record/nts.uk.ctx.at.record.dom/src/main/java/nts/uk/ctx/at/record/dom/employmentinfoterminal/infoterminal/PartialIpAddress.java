package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 
 * @author dungbn
 *
 */

@IntegerMaxValue(255)
@IntegerMinValue(0)
public class PartialIpAddress extends IntegerPrimitiveValue<PartialIpAddress> {
	
	private static final long serialVersionUID = 1L;

	public PartialIpAddress(Integer rawValue) {
		super(rawValue);
	}
}
