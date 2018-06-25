package nts.uk.ctx.pereg.dom.roles.functionauth.primitive;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author danpv
 * 利用できる／できない権限の機能説明文
 */
@StringMaxLength(200)
public class AuthFunctionDescription extends StringPrimitiveValue<AuthFunctionDescription>{

	private static final long serialVersionUID = 3941098052478730367L;

	public AuthFunctionDescription(String rawValue) {
		super(rawValue);
	}

}
