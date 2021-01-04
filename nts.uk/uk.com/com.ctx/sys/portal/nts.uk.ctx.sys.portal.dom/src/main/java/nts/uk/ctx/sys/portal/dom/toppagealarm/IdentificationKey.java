package nts.uk.ctx.sys.portal.dom.toppagealarm;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(36)
public class IdentificationKey extends StringPrimitiveValue<IdentificationKey> {
	private static final long serialVersionUID = 1L;
	
	public IdentificationKey(String rawValue) {
		super(rawValue);
	}

}
