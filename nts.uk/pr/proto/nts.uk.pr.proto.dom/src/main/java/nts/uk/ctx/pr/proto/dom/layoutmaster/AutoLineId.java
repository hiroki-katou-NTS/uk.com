package nts.uk.ctx.pr.proto.dom.layoutmaster;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLengh;

@StringMaxLengh(36)
public class AutoLineId extends StringPrimitiveValue<AutoLineId>{

	public AutoLineId(String rawValue) {
		super(rawValue);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
}
