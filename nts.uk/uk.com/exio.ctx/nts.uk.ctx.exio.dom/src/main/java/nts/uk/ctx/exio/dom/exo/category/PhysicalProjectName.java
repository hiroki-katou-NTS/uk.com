package nts.uk.ctx.exio.dom.exo.category;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;


@StringMaxLength(30)
public class PhysicalProjectName extends StringPrimitiveValue<PhysicalProjectName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhysicalProjectName(String rawValue) {
		super(rawValue);
	}

}