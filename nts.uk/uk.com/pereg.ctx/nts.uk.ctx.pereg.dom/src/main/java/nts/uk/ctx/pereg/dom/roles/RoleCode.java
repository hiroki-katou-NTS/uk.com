package nts.uk.ctx.pereg.dom.roles;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(4)
public class RoleCode extends StringPrimitiveValue<RoleCode>{

	public RoleCode(String rawValue) {
		super(rawValue);
	}
	
	private static final long serialVersionUID = 1L;

}
