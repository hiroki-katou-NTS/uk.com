package nts.uk.ctx.pereg.dom.roles;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(30)
public class RoleName extends StringPrimitiveValue<RoleName>{

	private static final long serialVersionUID = 1L;
	
	public RoleName(String rawValue) {
		super(rawValue);
	}
	

}
