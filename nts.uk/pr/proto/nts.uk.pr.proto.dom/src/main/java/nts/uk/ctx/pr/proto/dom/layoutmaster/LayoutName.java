package nts.uk.ctx.pr.proto.dom.layoutmaster;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLengh;

@StringMaxLengh(20)
public class LayoutName extends StringPrimitiveValue<LayoutName> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs.
	 * 
	 * @param rawValue raw value
	 */
	public LayoutName(String rawValue) {
		super(rawValue);
	}

}
