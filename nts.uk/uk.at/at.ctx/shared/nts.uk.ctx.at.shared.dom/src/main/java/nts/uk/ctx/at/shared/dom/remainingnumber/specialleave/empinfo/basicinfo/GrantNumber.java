package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

@IntegerMinValue(0)
@IntegerMaxValue(366)
public class GrantNumber extends IntegerPrimitiveValue<GrantNumber>{

	private static final long serialVersionUID = 1L;

	public GrantNumber(Integer rawValue) {
		super(rawValue);
	}

}
