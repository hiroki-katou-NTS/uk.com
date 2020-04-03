package nts.uk.ctx.hr.shared.dom.primitiveValue;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;


@StringMaxLength(50)
public class AttachedFileName extends StringPrimitiveValue<AttachedFileName> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	
	public AttachedFileName(String rawValue) {
		super(rawValue);
	}
}
