package nts.uk.ctx.pereg.dom.roles.functionauth.primitive;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;

/**
 * @author danpv
 * 利用可否権限の機能NO
 */
@IntegerMaxValue(999)
public class AuthFunctionNumber extends IntegerPrimitiveValue<AuthFunctionNumber> {
	
	private static final long serialVersionUID = 5434767933429488627L;

	public AuthFunctionNumber(Integer rawValue) {
		super(rawValue);
	}

}
