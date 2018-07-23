package nts.uk.ctx.pereg.dom.roles.functionauth.primitive;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author danpv
 * 利用可否権限の機能名
 */
@StringMaxLength(60)
public class AuthFunctionName extends StringPrimitiveValue<AuthFunctionName>{

	private static final long serialVersionUID = -3286002337162827269L;

	public AuthFunctionName(String rawValue) {
		super(rawValue);
	}

}
